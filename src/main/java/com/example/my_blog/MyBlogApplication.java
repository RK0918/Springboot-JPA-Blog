package com.example.my_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class MyBlogApplication {


    public static void main(String[] args) {
        // 콘솔의 일반 출력과 오류 출력을 UTF-8로 지정
        System.setOut(new java.io.PrintStream(
                System.out, true, java.nio.charset.StandardCharsets.UTF_8
        ));

        System.setErr(new java.io.PrintStream(
                System.err, true, java.nio.charset.StandardCharsets.UTF_8
        ));

// 이 아래에 기존 SpringApplication.run(...) 코드를 그대로 유지
        SpringApplication.run(MyBlogApplication.class, args);


    }


}
