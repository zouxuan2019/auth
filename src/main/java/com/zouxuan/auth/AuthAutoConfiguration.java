package com.zouxuan.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AuthProperties.class)
@ComponentScan
@Configuration
public class AuthAutoConfiguration {

    @Autowired
    private AuthProperties authProperties;

    @Bean
    @ConditionalOnMissingBean
    public ZouXuanAuthConfig zouXuanAuthConfig() {
        String server = authProperties.getServer();
        System.out.println( "auth lib:" + server );
        ZouXuanAuthConfig zouXuanAuthConfig = new ZouXuanAuthConfig();
        zouXuanAuthConfig.put( "server", server );
        return zouXuanAuthConfig;
    }


}
