package com.suxin.user.controller;

import com.suxin.user.client.OrderClient;
import com.suxin.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Classname UserController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/26 10:05
 * @Created by ASUS
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/index")
    public Map<String, String> index() {
        return userService.index();
    }

    @GetMapping("/fallback")
    public Map<String, String> fallbackMethod() {
        return userService.fallbackMethod();
    }

    @GetMapping("/flowTest")
    public Map<String, String> flowTest(Integer value) {
        return userService.flowTest(value);
    }

    @GetMapping("/downGradeTest")
    public Map<String, String> downGradeTest() {
        return userService.downGradeTest();
    }

}