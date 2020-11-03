package com.suxin.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Classname CustomSentinelGatewayBlockExceptionHandler
 * @Description [ 自定义异常 ]
 * @Author Tang
 * @Date 2020/10/30 14:36
 * @Created by ASUS
 */
public class CustomSentinelGatewayBlockExceptionHandler extends SentinelGatewayBlockExceptionHandler {

    private List<ViewResolver> viewResolvers;
    private List<HttpMessageWriter<?>> messageWriters;

    public CustomSentinelGatewayBlockExceptionHandler(
            List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(viewResolvers, serverCodecConfigurer);
        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    /**
     * 重写这个方法返回自定义异常
     */
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        serverHttpResponse.getHeaders().add("Content-Type",
                "application/json;charset=UTF-8");
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(
                JSONObject.toJSONString(new ErrorResult(
                        HttpStatus.TOO_MANY_REQUESTS.value(),
                        "你的访问太频繁了,请求被限流了！",
                        exchange.getRequest().getPath().value()
                )).getBytes(StandardCharsets.UTF_8)
        );
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return handleBlockedRequest(exchange, ex)
                .flatMap(response -> writeResponse(response, exchange));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        // 这地方会调用 DefaultBlockRequestHandler  可以自定义实现
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }

    private static class ErrorResult {
        private final int code;
        private final String message;
        private final String path;

        ErrorResult(int code, String message,String path) {
            this.code = code;
            this.message = message;
            this.path = path;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }
    }
}