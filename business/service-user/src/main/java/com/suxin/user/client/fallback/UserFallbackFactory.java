package com.suxin.user.client.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UserFallbackFactory
 * @Description [ 异常处理 ]
 * @Author Tang
 * @Date 2020/10/29 11:35
 * @Created by ASUS
 */
public class UserFallbackFactory {

    /**
     * 业务异常处理
     * @param value
     * @param throwable
     * @return
     */
    public static Map<String, String> sentinelThrowable(Integer value,Throwable throwable) {
        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "业务异常处理..." + value);
        return map;
    }

    /**
     * 限流处理
     * @param value
     * @param blockException
     * @return
     */
    public static Map<String, String> flowException(Integer value,BlockException blockException) {
        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "限流处理....");
        map.put("exception",blockException.getClass().getSimpleName());
        return map;
    }

    /**
     * 降级处理
     * @return
     */
    public static Map<String, String> degradeException(BlockException blockException) {
        Map<String, String> map = new HashMap<>(16);
        map.put("msg", "降级处理....");
        map.put("exception", blockException.getClass().getSimpleName());
        return map;
    }

}