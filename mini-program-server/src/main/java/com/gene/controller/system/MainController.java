package com.gene.controller.system;

import com.gene.controller.BaseController;
import com.gene.dto.base.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "登录注册相关的接口", tags = "main-controller")
@RestController
public class MainController extends BaseController {

    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    @GetMapping("/userInfo")
    public ResponseVo userInfo() {
        return ResponseVo.ofSuccess(getLoginUser());
    }
}
