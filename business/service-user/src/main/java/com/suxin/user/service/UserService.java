package com.suxin.user.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.suxin.user.client.OrderClient;
import com.suxin.user.client.fallback.UserFallbackFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname UserService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/29 13:40
 * @Created by ASUS
 */
@Service
public class UserService {

    @Resource
    private OrderClient orderClient;

    /**
     * 测试正常调用
     * @return
     */
    public Map<String, String> index() {
        return orderClient.index();
    }

    /**
     * 测试服务降级
     * @return
     */
    public Map<String, String> fallbackMethod() {
        return orderClient.fallbackMethod();
    }

    /**
     * 测试 限流
     * @param value
     * @return
     */
    @SentinelResource(
            value = "flowTest", // 定义一个资源名称
            fallbackClass = UserFallbackFactory.class, fallback = "sentinelThrowable",
            blockHandlerClass = UserFallbackFactory.class, blockHandler = "flowException"
    )
    public Map<String, String> flowTest(Integer value) {
        int i = 10 / value;
        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "flowTest..success!");
        return map;
    }

    /**
     * 测试熔断降级  控制台配置比例参数
     * @return
     */
    @SentinelResource(
            value = "downGradeTest",
            blockHandlerClass = UserFallbackFactory.class, blockHandler = "degradeException"
    )
    public Map<String, String> downGradeTest() {

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "downGradeTest..success!");
        return map;
    }

}