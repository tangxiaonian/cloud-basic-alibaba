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

    @Resource
    private OrderClient orderClient;

    @GetMapping("/index")
    public Map<String, String> index() {
        return orderClient.index();
    }

    @GetMapping("/fallback")
    public Map<String, String> fallbackMethod() {
        return orderClient.fallbackMethod();
    }

    @GetMapping("/test")
    public String sentinelTest(Integer value) {
        return userService.sentinelTest(value);
    }

}