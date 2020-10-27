package com.suxin.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname UserApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/26 10:04
 * @Created by ASUS
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);

    }

}