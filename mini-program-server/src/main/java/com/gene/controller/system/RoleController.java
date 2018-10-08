package com.gene.controller.system;

import com.gene.dto.base.DataGrid;
import com.gene.dto.base.ResponseVo;
import com.gene.entity.system.Role;
import com.gene.service.system.impl.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Api(value = "角色相关的接口", tags = "role")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @ApiOperation(value = "查询所有角色")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    @GetMapping()
    public DataGrid<Role> list(String keyword) {
        List<Role> list = roleServiceImpl.list(false);
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = next.getRoleId().contains(keyword) || next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return new DataGrid<>(list);
    }

    @ApiOperation(value = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public ResponseVo add(Role role) {
        if (roleServiceImpl.add(role)) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError("添加失败");
        }
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public ResponseVo update(Role role) {
        if (roleServiceImpl.update(role)) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError("修改失败！");
        }
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable("id") String roleId) {
        if (roleServiceImpl.updateState(roleId, 1)) {
            return ResponseVo.ofSuccess();
        }
        return ResponseVo.ofError("删除失败");
    }
}
