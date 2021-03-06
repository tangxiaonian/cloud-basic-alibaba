package com.suxin.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "hello order index!" + port);
        return map;
    }

    @GetMapping("/fallback")
    public Map<String, String> fallbackMethod() {
        throw new RuntimeException("error...");
    }

    @GetMapping("/downGradeTest")
    public Map<String, String> downGradeTest() {
        System.out.println( Thread.currentThread().getName() + "进来了...等待中..." );
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( Thread.currentThread().getName() + "进来了...等待结束..." );
        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "hello order downGradeMethod!" + port);
        return map;
    }

}