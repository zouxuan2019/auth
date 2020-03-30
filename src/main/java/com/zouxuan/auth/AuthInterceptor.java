package com.zouxuan.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    AuthImp authImp;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println( "interceptor preHandle" );
        System.out.println( "dispatcher name:" + request.getDispatcherType().name() );
        if (!request.getDispatcherType().name().equals( "REQUEST" )) {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PermissionScope methodAnnotation = handlerMethod.getMethodAnnotation( PermissionScope.class );
            System.out.println( "authinterceptormethod:" + handlerMethod.getMethod() );
            if (null == methodAnnotation) {
                System.out.println( "annoation is null" );
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
                return false;
            }
            Arrays.stream( methodAnnotation.value() ).forEach( x -> System.out.println( "AuthInterceptor:" + x ) );
            boolean isAuthorized = authImp.authorize( request.getHeader( "Authorization" ), methodAnnotation );
            if (!isAuthorized) {
                System.out.println( "unauthorized" );
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
            }
            return isAuthorized;
        }
        return false;
    }

}
