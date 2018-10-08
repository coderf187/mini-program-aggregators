package com.gene.controller.system;

import com.gene.controller.BaseController;
import com.gene.dto.base.DataGrid;
import com.gene.dto.base.ResponseVo;
import com.gene.entity.system.Role;
import com.gene.entity.system.User;
import com.gene.service.system.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "用户相关的接口", tags = "user")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 这里参数过多，并且参数含有中文，建议用post请求，用restful风格解决不了需求时，建议不要强行使用restful
     * 加了一个/query是避免跟添加用户接口冲突
     */
    @ApiOperation(value = "查询所有用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/query")
    public DataGrid list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtils.isBlank(searchValue)) {
            searchKey = null;
        }
        return userServiceImpl.list(page, limit, true, searchKey, searchValue);
    }

    @ApiOperation(value = "添加用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User"),
            @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public ResponseVo add(User user, String roleId) {
        List<Role> roleIds = new ArrayList<>();
        String[] split = roleId.split(",");
        for (String t : split) {
            Role role = new Role();
            role.setRoleId(t);
            roleIds.add(role);
        }
        user.setRoles(roleIds);
        user.setPassword("123456");
        if (userServiceImpl.add(user)) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError();
        }
    }

    @ApiOperation(value = "修改用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User"),
            @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public ResponseVo update(User user, String roleId) {
        if ("admin".equals(user.getUserId())) {
            return ResponseVo.ofError("演示系统不能操作admin");
        }
        List<Role> roleIds = new ArrayList<>();
        String[] split = roleId.split(",");
        for (String t : split) {
            Role role = new Role();
            role.setRoleId(t);
            roleIds.add(role);
        }
        user.setRoles(roleIds);
        if (userServiceImpl.update(user)) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError("修改失败");
        }
    }

    @ApiOperation(value = "修改用户状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "state", value = "状态：0正常，1冻结", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping("/state")
    public ResponseVo updateState(String userId, Integer state) {
        if (userServiceImpl.updateState(userId, state)) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError();
        }
    }

    @ApiOperation(value = "修改自己密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPsw", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPsw", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping("/psw")
    public ResponseVo updatePsw(String oldPsw, String newPsw) {
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(oldPsw);
        if (finalSecret.equals(getLoginUser().getPassword())) {
            return ResponseVo.ofError("原密码输入不正确");
        }
        if (userServiceImpl.updatePsw(getLoginUserId(), newPsw)) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError("修改失败");
        }
    }

    @ApiOperation(value = "重置密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping("/psw/{id}")
    public ResponseVo resetPsw(@PathVariable("id") String userId) {
        if ("admin".equals(userId)) {
            return ResponseVo.ofError("演示系统不能操作admin");
        }
        if (userServiceImpl.updatePsw(userId, "123456")) {
            return ResponseVo.ofSuccess();
        } else {
            return ResponseVo.ofError("重置失败");
        }
    }
}
