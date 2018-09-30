package com.gene.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gene.entity.system.Authorities;
import com.gene.entity.system.RoleAuthorities;
import com.gene.mapper.system.AuthoritiesMapper;
import com.gene.mapper.system.RoleAuthoritiesMapper;
import com.gene.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthoritiesServiceImpl {
    @Autowired
    private AuthoritiesMapper authoritiesMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;

    public List<String> listByUserId(String userId) {
        return authoritiesMapper.listByUserId(userId);
    }

    public List<Authorities> list() {
        return authoritiesMapper.selectList(null);
    }

    public List<String> listByRoleId(List<String> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return authoritiesMapper.listByRoleId(roleIds);
    }

    public List<String> listByRoleId(String roleId) {
        List<String> roleIds = new ArrayList<>();
        if (roleId != null && !roleId.trim().isEmpty()) {
            roleIds.add(roleId);
        }
        return listByRoleId(roleIds);
    }

    public boolean add(Authorities authorities) {
        authorities.setCreateTime(new Date());
        return authoritiesMapper.insert(authorities) > 0;
    }

    public boolean add(List<Authorities> authorities) {
        authoritiesMapper.delete(null);
        for (Authorities one : authorities) {
            one.setCreateTime(new Date());
            authoritiesMapper.insert(one);
        }
        roleAuthoritiesMapper.deleteTrash();
        return true;
    }

    public boolean addRoleAuth(String roleId, String authId) {
        RoleAuthorities roleAuthorities = new RoleAuthorities();
        roleAuthorities.setId(UUIDUtil.randomUUID32());
        roleAuthorities.setRoleId(roleId);
        roleAuthorities.setAuthority(authId);
        roleAuthorities.setCreateTime(new Date());
        return roleAuthoritiesMapper.insert(roleAuthorities) > 0;
    }

    public boolean deleteRoleAuth(String roleId, String authId) {
        return roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("role_id", roleId).eq("authority", authId)) > 0;
    }

}
