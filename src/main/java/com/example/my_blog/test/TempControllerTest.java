package com.example.my_blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// @컨트롤러는 해당 경로(혹은 yml에 설정한 경로) 이하에 있는 file을 return
public class TempControllerTest {

    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("tempHome()");
        // 파일리턴 기본 경로 : src/main/resources/static
        // 리턴명 : /homt.html
        // 풀경로 : src/main/resource/static/home.html
        return "/home.html";
    }

    // @RestController는 string에서 문자 그 자체를 return했다면
    // @Controller는 해당 경로 이하에 있는 파일을 return(home.html)

    @GetMapping("/temp/img")
    public String tempImg() {
        return "/a.png";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        // yml 설정 덕분에 "test"만 반환해도
        // 풀네임 : /WEB-INF/views/test.jsp를 찾습니다.
        return "test";
    }



}
