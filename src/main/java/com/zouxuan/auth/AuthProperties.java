package com.zouxuan.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("zouxuan.auth")
@Component
@Data
public class AuthProperties {
    private String server;
}
