package com.example.admin.controller;

import com.example.admin.common.result.Result;
import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result<List<User>> list() {
        return Result.success(userService.list());
    }

    // 新增用户
    @PostMapping(value = "/add", consumes = "application/json")
    public Result<String> add(@RequestBody User user) {
        // 自动生成主键，清空手动传入的 id
        user.setId(null);
        // 校验必填字段
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("用户名和密码不能为空");
        }
        boolean ok = userService.save(user);
        return ok ? Result.success("添加成功") : Result.error("添加失败");
    }

    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody User user) {
        if (userService.getById(user.getId()) == null) {
            return Result.error("用户不存在");
        }
        user.setUsername(null);
        boolean ok = userService.updateById(user);
        return ok ? Result.success("修改成功") : Result.error("修改失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean ok = userService.removeById(id);
        return ok ? Result.success("删除成功") : Result.error("删除失败");
    }
}