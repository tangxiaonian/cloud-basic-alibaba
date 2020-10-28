package com.suxin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname GateWayApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/27 14:57
 * @Created by ASUS
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        ErrorWebFluxAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
})
public class GateWayApplication {

    public static void main(String[] args) {

        SpringApplication.run(GateWayApplication.class,args);

    }

}