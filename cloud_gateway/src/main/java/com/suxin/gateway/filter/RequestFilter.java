package com.suxin.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Classname RequestFilter
 * @Description [ 请求日志过滤器 ]
 * @Author Tang
 * @Date 2020/10/21 16:14
 * @Created by ASUS
 */
@Component
public class RequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println( "执行过滤器了...." );
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static String getRemoteIp(ServerHttpRequest request) {
        String remoteAddr = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        String forwarded = request.getHeaders().getFirst("X-Forwarded-For");
        String realIp = request.getHeaders().getFirst("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}