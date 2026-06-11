package com.example.my_blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    
    
    @GetMapping({"", "/"}) // 아무것도 안적을 때, 슬래쉬(/) 두 가지 경우
    public String index() {
        // WEB-INF/views/index.jsp -> yml 참고  prefix, suffix

        return "index";
    }
}
