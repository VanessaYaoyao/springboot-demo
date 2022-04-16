package com.example.controller;

import com.example.entity.resp.RestBean;
import com.example.service.AccountService;
import com.example.service.VerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Api(tags = "用户验证",description = "用户登录成功、失败的返回实体,注册以及验证码发送等")
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Resource
    VerifyService verifyService;
    @Resource
    AccountService accountService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "邮件发送成功"),
            @ApiResponse(code = 500, message = "邮件发送失败")   //不同返回状态码描述
    })
    @ApiOperation("请求邮件验证码")
    @GetMapping("/verify-code")
    public RestBean<Void> verifyCode(@RequestParam("email") String email){
        try {
            verifyService.sendVerifyCode(email);
            return new RestBean<Void>(200,"发送成功");
        }catch (Exception e){
            return new RestBean<Void>(500,"发送失败");
        }
    }
    @PostMapping(value = "/register")
    public RestBean<Void> register(String username,
                         String password,
                         String email,
                         String verify){
        if(verifyService.doVerify(email, verify)){
            accountService.createAccount(username, password);
            return new RestBean<Void>(200,"注册成功");
        }else {
            return new RestBean<Void>(403,"注册失败");
        }
    }
    @PostMapping("login-success")
    public RestBean<Void> loginSuccess(){
        return new RestBean<>(200,"登录成功");
    }

    @PostMapping("login-failure")
    public RestBean<Void> loginFailure(){
        return new RestBean<>(304,"登录失败，用户名或密码错误！");
    }

    @GetMapping("logout-success")
    public RestBean<Void> logoutSuccess(){
        return new RestBean<>(200,"退出成功");
    }

    @ApiIgnore
    @RequestMapping("/access-deny")
    public RestBean<Void> accessDeny(){
        return new RestBean<>(401,"未验证，请先登录！");
    }
}
