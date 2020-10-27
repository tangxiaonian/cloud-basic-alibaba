package com.suxin.user.client;

import com.suxin.user.client.fallback.OrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Classname OrderClient
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/26 10:16
 * @Created by ASUS
 */
@FeignClient(value = "service-order",path = "/order",fallbackFactory = OrderFallbackFactory.class)
public interface OrderClient {

    @GetMapping("/index")
    public String index();

    @GetMapping("/fallback")
    public Map<String, String> fallbackMethod();

}
