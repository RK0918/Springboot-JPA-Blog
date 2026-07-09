package com.example.my_blog.service;


import com.example.my_blog.model.User;
import com.example.my_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록, IoC를 해줌
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(User user) {

        userRepository.save(user);
    }

    @Transactional(readOnly = true) // Select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지시킬 수 있음)
    public User 로그인(User user) {
        //  userRepository에 해당 기능이 없어서 따로 만들어줘야 함.
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());


    }

}
