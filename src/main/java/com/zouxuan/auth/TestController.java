package com.zouxuan.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test1")
    @PermissionScope(value = "admin")
    public void test1() {
        System.out.println( "ok" );
    }

    @GetMapping("/anon/test2")
    public void test2() {
        System.out.println( "bbb" );
    }
}
