package com.suxin.gateway.exception;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CustomErrorWebExceptionHandler
 * @Description [ webflux的自定义异常处理器 ]
 * @Author Tang
 * @Date 2020/10/3 16:22
 * @Created by ASUS
 */
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                          ResourceProperties resourceProperties,
                                          ServerProperties serverProperties,
                                          ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, serverProperties.getError(),
                applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        Map<String, Object> errorAttributes = new HashMap<>(8);
        errorAttributes.put("message", error.getMessage());
        errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorAttributes.put("method", request.methodName());
        errorAttributes.put("path", request.path());
        return errorAttributes;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        // 使用json输出
        return RouterFunctions.route((request) ->  true,this::renderErrorResponse);
    }

}