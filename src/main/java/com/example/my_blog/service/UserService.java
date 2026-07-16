package com.example.my_blog.service;


import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록, IoC를 해줌
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 패스워드를 해쉬함수로 바꿔줌
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 1234 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }











/* 전통적인 로그인방식
    @Transactional(readOnly = true) // Select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지시킬 수 있음)
    public User 로그인(User user) {
        //  userRepository에 해당 기능이 없어서 따로 만들어줘야 함.
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());


    }
*/

}
