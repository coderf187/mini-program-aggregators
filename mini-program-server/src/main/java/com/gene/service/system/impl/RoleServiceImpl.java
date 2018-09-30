package com.gene.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gene.entity.system.Role;
import com.gene.entity.system.UserRole;
import com.gene.exception.BusinessException;
import com.gene.mapper.system.RoleAuthoritiesMapper;
import com.gene.mapper.system.RoleMapper;
import com.gene.mapper.system.UserRoleMapper;
import com.gene.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public String[] getRoleIds(String userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(new EntityWrapper().eq("user_id", userId));
        String[] roleIds = new String[userRoles.size()];
        for (int i = 0; i < userRoles.size(); i++) {
            roleIds[i] = userRoles.get(i).getRoleId();
        }
        return roleIds;
    }

    public List<Role> list(boolean showDelete) {
        Wrapper wrapper = new EntityWrapper();
        if (!showDelete) {
            wrapper.eq("is_delete", 0);
        }
        return roleMapper.selectList(wrapper.orderBy("create_time", true));
    }

    public boolean add(Role role) {
        role.setRoleId(UUIDUtil.randomUUID32());
        role.setCreateTime(new Date());
        return roleMapper.insert(role) > 0;
    }

    public boolean update(Role role) {
        return roleMapper.updateById(role) > 0;
    }

    public boolean updateState(String roleId, int isDelete) {
        if (isDelete != 0 && isDelete != 1) {
            throw BusinessException.ofErrorBadRequest();
        }
        Role role = new Role();
        role.setRoleId(roleId);
        role.setIsDelete(isDelete);
        boolean rs = roleMapper.updateById(role) > 0;
        if (rs) {
            //删除角色的权限
            roleAuthoritiesMapper.delete(new EntityWrapper().eq("role_id", roleId));
        }
        return rs;
    }

    public Role getById(String roleId) {
        return roleMapper.selectById(roleId);
    }

    public boolean delete(String roleId) {
        return roleMapper.deleteById(roleId) > 0;
    }
}
