package com.gene.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gene.dto.base.DataGrid;
import com.gene.dto.base.ResponseVo;
import com.gene.entity.system.Role;
import com.gene.entity.system.User;
import com.gene.entity.system.UserRole;
import com.gene.exception.BusinessException;
import com.gene.mapper.system.RoleMapper;
import com.gene.mapper.system.UserMapper;
import com.gene.mapper.system.UserRoleMapper;
import com.gene.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    public DataGrid list(int pageNum, int pageSize, boolean showDelete, String column, String value) {
        Wrapper<User> wrapper = new EntityWrapper<User>();
        if (StringUtils.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        if (!showDelete) {
            wrapper.eq("state", 0);
        }
        Page<User> userPage = new Page<>(pageNum, pageSize);
        List<User> userList = userMapper.selectPage(userPage, wrapper);
        // 查询user的角色
        List<String> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        List<Role> roles = roleMapper.selectList(null);
        List<UserRole> userRoles = userRoleMapper.selectList(new EntityWrapper().in("user_id", userIds));
        for (User one : userList) {
            List<Role> tempUrs = new ArrayList<>();
            for (UserRole ur : userRoles) {
                if (one.getUserId().equals(ur.getUserId())) {
                    for (Role r : roles) {
                        if (ur.getRoleId().equals(r.getRoleId())) {
                            tempUrs.add(r);
                        }
                    }
                }
            }
            one.setRoles(tempUrs);
        }
        return new DataGrid(userPage.getTotal(), userList);
    }

    public boolean add(User user) throws BusinessException {
        String userId = UUIDUtil.randomUUID32();
        user.setUserId(userId);
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(finalSecret);
        user.setState(0);
        user.setCreateTime(new Date());
        try {
            boolean rs = userMapper.insert(user) > 0;
            if (rs) {
                addUserRole(userId, user.getRoles());
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("账号已经存在");
        }
    }

    public boolean update(User user) {
        boolean rs = userMapper.updateById(user) > 0;
        if (rs) {
            userRoleMapper.delete(new EntityWrapper().eq("user_id", user.getUserId()));
            addUserRole(user.getUserId(), user.getRoles());
        }
        return rs;
    }

    private void addUserRole(String userId, List<Role> roles) {
        if (roles == null) {
            return;
        }
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setId(UUIDUtil.randomUUID32());
            userRole.setUserId(userId);
            userRole.setRoleId(role.getRoleId());
            userRole.setCreateTime(new Date());
            userRoleMapper.insert(userRole);
        }
    }

    public boolean updateState(String userId, int state) throws BusinessException {
        if (state != 0 && state != 1) {
            throw BusinessException.ofErrorBadRequest();
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        return userMapper.updateById(user) > 0;
    }

    public boolean updatePsw(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(finalSecret);
        return userMapper.updateById(user) > 0;
    }

    public User getById(String userId) {
        return userMapper.selectById(userId);
    }

    public boolean delete(String userId) {
        return userMapper.deleteById(userId) > 0;
    }
}
