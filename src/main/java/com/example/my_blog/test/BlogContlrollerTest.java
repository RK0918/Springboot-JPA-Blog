package com.example.my_blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 스프링이 com.example.my_blog 패키지 이하를 스캔
// 특정 어노테이션이 붙어있는 클래스 파일들을 new해서(IoC 제어역전)
// 스프링 컨테이너에 관리
@RestController
public class BlogContlrollerTest {
// 지금 build.gradle에서 security 주석처리 해놨으니 나중에 풀어줘야됨.
    @GetMapping("/test/hello")
    public String hello() {
        return "<h1> hello spring boot</h1>";
    }
}
