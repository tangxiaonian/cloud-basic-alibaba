package com.suxin.user.controller;

import com.suxin.user.client.OrderClient;
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
    private OrderClient orderClient;

    @GetMapping("/index")
    public String index() {
        return orderClient.index();
    }

    @GetMapping("/fallback")
    public Map<String, String> fallbackMethod() {
        return orderClient.fallbackMethod();
    }

}