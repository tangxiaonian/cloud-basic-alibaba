package com.suxin.user.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.suxin.user.client.fallback.UserFallbackFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname UserService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/29 13:40
 * @Created by ASUS
 */
@Service
public class UserService {

    @SentinelResource(
            value = "sentinelTest", // 定义一个资源名称
            fallbackClass = UserFallbackFactory.class, fallback = "sentinelThrowable",
            blockHandlerClass = UserFallbackFactory.class, blockHandler = "sentinelBlockException"
    )
    public String sentinelTest(Integer value) {

        int i = 10 / value;

        return "sentinelTest success!";
    }

}