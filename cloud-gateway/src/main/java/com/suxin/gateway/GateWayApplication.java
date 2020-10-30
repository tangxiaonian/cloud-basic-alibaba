package com.suxin.gateway;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname GateWayApplication
 * @Description [ GateWayApplication ]
 * @Author Tang
 * @Date 2020/10/27 14:57
 * @Created by ASUS
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        ErrorWebFluxAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class GateWayApplication {

    public static void main(String[] args) {
        // 设置应用类型为网关 默认是 0
        System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, "1");
        SpringApplication.run(GateWayApplication.class,args);
    }
}