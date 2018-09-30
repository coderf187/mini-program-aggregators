package com.gene.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gene.entity.system.RoleAuthorities;

public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {

    int deleteTrash();
}
