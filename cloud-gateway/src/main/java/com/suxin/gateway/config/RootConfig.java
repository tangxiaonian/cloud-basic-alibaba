package com.suxin.gateway.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.suxin.core.config.JwtAuthenticationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Classname RootConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/27 15:31
 * @Created by ASUS
 */
@Configuration
public class RootConfig {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @Bean
    public JwtAuthenticationConfig jwtAuthenticationConfig() {
        return new JwtAuthenticationConfig();
    }

    @PostConstruct
    public void initRule() {
        // 动态从Nacos中拉取sentinel规则配置
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(
                serverAddr, "DEFAULT_GROUP", "gateway-sentinel.json",
                (source) -> {
                    return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {});
                });
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

}