package com.suxin.gateway.config;

import com.suxin.core.config.JwtAuthenticationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RootConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/10/27 15:31
 * @Created by ASUS
 */
@Configuration
public class RootConfig {

    @Bean
    public JwtAuthenticationConfig jwtAuthenticationConfig() {
        return new JwtAuthenticationConfig();
    }

}