package com.example.controller;

import com.example.entity.Account;
import com.example.entity.resp.RestBean;
import com.example.repo.AccountRepository;
import io.swagger.annotations.Api;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Api(tags = "用户信息",description = "所有的用户信息包括更新、查询等")
@RestController
@RequestMapping("/api/user")
public class AccountApiController {

    @Resource
    AccountRepository repository;

    @GetMapping("/info")
    public RestBean<Account> info(){
        SecurityContext context = SecurityContextHolder.getContext();
        Account account = repository.findAccountByUsername(context.getAuthentication().getName());
        account.setPassword("");
        return new RestBean<>(200, "请求成功",account);
    }
}
