package com.suxin.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname OrderController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/26 10:05
 * @Created by ASUS
 */
@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/index")
    public Map<String, String> index() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "hello order index!" + port);
        return map;
    }

    @GetMapping("/fallback")
    public Map<String, String> fallbackMethod() {
        throw new RuntimeException("error...");
    }

}