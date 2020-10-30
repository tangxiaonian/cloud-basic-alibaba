package com.suxin.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suxin.core.config.JwtAuthenticationConfig;
import com.suxin.core.domain.Token;
import com.suxin.core.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LoginFilter
 * @Description [ 原先的登录过滤器 ]
 * @Author Tang
 * @Date 2020/10/21 16:10
 * @Created by ASUS
 */
@Slf4j
//@Component
public class LoginFilter implements GlobalFilter, Ordered {

    @Resource
    public JwtAuthenticationConfig jwtAuthenticationConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest exchangeRequest = exchange.getRequest();
        String uri = exchangeRequest.getURI().getPath();
        // 是不是排除路径
        if (isExcludeUrl(uri)) {
            return chain.filter(exchange);
        }
        // 验证token
        String token = exchangeRequest.getHeaders()
                .getFirst(jwtAuthenticationConfig.getHeader());
        if (!StringUtils.isEmpty(token) && token.startsWith(jwtAuthenticationConfig.getPrefix())) {
            // 获取真实token
            String realToken = token.replace(jwtAuthenticationConfig.getPrefix() + " ",
                    "");
            if (JwtUtil.isValid(jwtAuthenticationConfig, realToken)) {
                Token auth = null;
                try {
                    auth = JwtUtil.getToken(jwtAuthenticationConfig, realToken);
                    log.info("User : {} + UserId : {} + Client IP : {}", auth.getUsername(), auth.getUserId(),
                            RequestFilter.getRemoteIp(exchangeRequest));
                    return chain.filter(exchange);
                } catch (Exception e) {
                    // 用户信息失效了
                    log.info("token 解析失败...");
                }
            }
        }
        ServerHttpResponse exchangeResponse = exchange.getResponse();
        return exchangeResponse.writeWith(responseData(exchangeResponse));
    }

    /**
     * 输出数据
     * @param exchangeResponse
     * @return
     */
    private Mono<DataBuffer> responseData(ServerHttpResponse exchangeResponse) {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("code", "401");
        responseData.put("message", "非法请求");
        responseData.put("case", "token is empty");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = null;
        try {
            //   转化为json输出
            bytes = objectMapper.writeValueAsBytes(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 返回给浏览器
        HttpHeaders httpHeaders = exchangeResponse.getHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        exchangeResponse.setStatusCode(HttpStatus.BAD_REQUEST);
        DataBufferFactory bufferFactory = exchangeResponse.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(bytes);
        return Mono.just(dataBuffer);
    }


    /**
     * 排除路径
     * @param uri
     * @return
     */
    private boolean isExcludeUrl(String uri) {
        return Arrays.stream(
                jwtAuthenticationConfig.getExcludeUrlPrefix().split(",")
        ).anyMatch(uri::startsWith)
                ||
                Arrays.stream(
                        jwtAuthenticationConfig.getExcludeUrlSuffix().split(",")
                ).anyMatch(uri::endsWith)
                ||
                Arrays.asList(
                        jwtAuthenticationConfig.getUrl().split(",")
                ).contains(uri);
    }

    @Override
    public int getOrder() {
        return 1;
    }

}