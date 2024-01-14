package com.thesis.sys.controller;

import com.thesis.common.vo.Result;
import com.thesis.sys.entity.News;
import com.thesis.sys.entity.User;
import com.thesis.sys.service.IUserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangyang
 * @since 2024-01-09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @GetMapping("/all")
    public Result<List<User>> getALlUser() {
       List<User> list = userService.list();
        return Result.success(list);
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        // 根据token获取用户信息，redis
        System.out.println("Token received: " + token);
        Map<String, Object> data = userService.getUserInfo(token);
        System.out.println(data);
        if (data != null) {
            System.out.println("nihao");
            return Result.success(data);

        }
        return Result.fail(20003, "登录信息无效，请重新登录");
    }
}
