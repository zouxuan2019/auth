package com.zouxuan.auth;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthImp {
    private AuthProperties authProperties;

    public AuthImp(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }


    public boolean authorize(String token, PermissionScope permissionScope) {
        System.out.println( "authorize:" + authProperties.getServer() );
        return
               permissionScope.full()
                || (token != null && Arrays.stream( permissionScope.value() ).anyMatch( token::contains ));
    }
}
