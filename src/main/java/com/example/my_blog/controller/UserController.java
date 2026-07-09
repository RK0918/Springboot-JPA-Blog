package com.example.my_blog.controller;


import com.example.my_blog.dto.ResponseDto;
import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    
    @GetMapping("/user/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/user/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }


}