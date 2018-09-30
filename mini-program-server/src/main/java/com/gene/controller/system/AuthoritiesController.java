package com.gene.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gene.controller.BaseController;
import com.gene.dto.base.ResponseVo;
import com.gene.entity.system.Authorities;
import com.gene.service.system.impl.AuthoritiesServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(value = "权限管理相关的接口", tags = "authorities")
@RestController
@RequestMapping("/authorities")
public class AuthoritiesController extends BaseController {
    @Autowired
    private AuthoritiesServiceImpl authoritiesServiceImpl;

    @ApiOperation(value = "同步权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "权限列表json", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/sync")
    public ResponseVo add(String json) {
        try {
            List<Authorities> list = new ArrayList<>();
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject paths = jsonObject.getJSONObject("paths");
            Set<String> pathsKeys = paths.keySet();
            for (String pathKey : pathsKeys) {
                JSONObject apiObject = paths.getJSONObject(pathKey);
                Set<String> apiKeys = apiObject.keySet();
                for (String apiKey : apiKeys) {
                    JSONObject methodObject = apiObject.getJSONObject(apiKey);
                    Authorities authorities = new Authorities();
                    authorities.setAuthority(apiKey + ":" + pathKey);
                    authorities.setAuthorityName(methodObject.getString("summary"));
                    list.add(authorities);
                }
            }
            authoritiesServiceImpl.add(list);
            return ResponseVo.ofSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.ofError("同步失败");
        }
    }

    @ApiOperation(value = "查询所有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping
    public ResponseVo<List<JSONObject>> list(String roleId) {
        List<JSONObject> list = new ArrayList<>();
        List<Authorities> authorities = authoritiesServiceImpl.list();
        List<String> roleAuths = authoritiesServiceImpl.listByRoleId(roleId);
        for (Authorities one : authorities) {
            JSONObject json = JSON.parseObject(JSON.toJSONString(one));
            json.put("checked", 0);
            for (String roleAuth : roleAuths) {
                if (one.getAuthority().equals(roleAuth)) {
                    json.put("checked", 1);
                    break;
                }
            }
            list.add(json);
        }
        return ResponseVo.ofSuccess(list);
    }

    @ApiOperation(value = "给角色添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "authId", value = "权限id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/role")
    public ResponseVo addRoleAuth(String roleId, String authId) {
        if (authoritiesServiceImpl.addRoleAuth(roleId, authId)) {
            return ResponseVo.ofSuccess();
        }
        return ResponseVo.ofError();
    }

    @ApiOperation(value = "移除角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "authId", value = "权限id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/role")
    public ResponseVo deleteRoleAuth(String roleId, String authId) {
        if (roleId.equals("admin")) {
            return ResponseVo.ofError("演示系统不能对管理员操作");
        }
        if (authoritiesServiceImpl.deleteRoleAuth(roleId, authId)) {
            return ResponseVo.ofSuccess();
        }
        return ResponseVo.ofError();
    }
}
