package com.gene.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gene.entity.system.User;

public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
