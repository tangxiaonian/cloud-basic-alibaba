package com.suxin.user.client.fallback;

import com.suxin.user.client.OrderClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname OrderFallbackFactory
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/26 10:17
 * @Created by ASUS
 */
@Component
public class OrderFallbackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {
            @Override
            public Map<String, String> index() {
                Map<String, String> map = new HashMap<>(16);
                map.put("msg", "远程调用index失败...");
                return map;
            }

            @Override
            public Map<String, String> fallbackMethod() {
                Map<String, String> map = new HashMap<>(16);
                map.put("msg", "远程调用fallbackMethod失败...");
                return map;
            }

            @Override
            public Map<String, String> downGradeTest() {
                Map<String, String> map = new HashMap<>(16);
                map.put("msg", "远程调用downGradeTest失败...");
                return map;
            }
        };
    }
}