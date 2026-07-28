package com.example.my_blog.controller;


import com.example.my_blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"", "/"}) // 아무것도 안적을 때, 슬래쉬(/) 두 가지 경우
    public String index(@AuthenticationPrincipal PrincipalDetail principal) { // 컨트롤러에서 세션을 어떻게 찾는지 ?
        // WEB-INF/views/index.jsp -> yml 참고  prefix, suffix

        System.out.println("로그인 사용자 아이디 : " + principal.getUsername());
        return "index";
    }
}
