package com.suxin.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname IndexController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/28 9:10
 * @Created by ASUS
 */
@RestController
@RequestMapping("/gateway")
public class IndexController {

    @GetMapping("/index")
    public String index() {
        throw new RuntimeException("error test...");
    }

}