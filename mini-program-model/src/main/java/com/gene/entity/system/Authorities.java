package com.gene.entity.system;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

/**
 * 权限表
 */
@TableName("sys_authorities")
public class Authorities implements GrantedAuthority {
    private static final long serialVersionUID = -6058060376656180793L;
    /**
     * 权限关键字
     */
    @TableId
    private String authority;
    /**
     * 权限名称
     */
    private String authorityName;
    /**
     * 创建时间
     */
    private Date createTime;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
