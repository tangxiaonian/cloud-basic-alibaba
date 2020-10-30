package com.suxin.user.client.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @Classname UserFallbackFactory
 * @Description [ 异常处理 ]
 * @Author Tang
 * @Date 2020/10/29 11:35
 * @Created by ASUS
 */
public class UserFallbackFactory {

    public static String sentinelThrowable(Integer value,Throwable throwable) {
        return "处理业务异常..." + value;
    }

    public static String sentinelBlockException(Integer value,BlockException blockException) {
        return "处理BlockException的异常...." + value;
    }

}