package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.admin.common.result.Result;
import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import com.example.admin.utils.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        wrapper.eq(User::getPassword, user.getPassword());

        User loginUser = userService.getOne(wrapper);
        if (loginUser == null) {
            return Result.error("用户名或密码错误");
        }

        String token = jwtUtil.createToken(loginUser);
        return Result.success(token);
    }
}