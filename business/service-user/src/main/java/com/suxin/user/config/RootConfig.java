package com.suxin.user.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Classname RootConfig
 * @Description [ 根配置类 ]
 * @Author Tang
 * @Date 2020/10/29 15:49
 * @Created by ASUS
 */
@Configuration
public class RootConfig {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @PostConstruct
    public void initRule() {
        // 动态从Nacos中拉取sentinel限流的规则配置
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(
                serverAddr, "DEFAULT_GROUP", "service-user-flowRule.json",
                (source) -> {
                    return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {});
                });
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        // 动态从Nacos中拉取熔断降级的规则配置
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(
                serverAddr, "DEFAULT_GROUP", "service-user-degradeRule.json",
                (source) -> {
                    return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {});
                });
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
    }

}