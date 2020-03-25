package com.zouxuan.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ComponentScan
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    AuthImp authImp;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println( "authinterceptor being called" );
        String url = request.getRequestURI();
        boolean isAnon = url.contains( "/anon/" );
        if (isAnon) return true;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PermissionScope methodAnnotation = handlerMethod.getMethodAnnotation( PermissionScope.class );
            if (null == methodAnnotation) {
                return false;
            }
            boolean isAuthorized = authImp.authorize( url, request.getHeader( "Authorization" ), methodAnnotation );
            if (!isAuthorized) {
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
            }
            return isAuthorized;
        }
        return false;
    }

}
