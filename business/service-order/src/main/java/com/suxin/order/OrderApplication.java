package com.suxin.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname OrderApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/26 9:47
 * @Created by ASUS
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderApplication.class, args);

    }

}