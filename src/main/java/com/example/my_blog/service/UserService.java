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

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // select 를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주기 때문임.
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패"); // user id값이 null일 수도 있으니

        });

        // 만약 user id값이 null 이 아닐 경우 정상적으로 persistance에
        // userRepository에서 찾은 user id의 값이 할당될 것
        String rawPassword = user.getPassword(); // 1234 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());
        // 회원수정 함수 종료 시 => 서비스 종료 => 트랜잭션이 종료 => commit이 자동으로 됨
        // 영속화된 persistance 객체의 변화가 감지 => 더티체킹 => update문을 날려줌 

    }











/* 전통적인 로그인방식
    @Transactional(readOnly = true) // Select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지시킬 수 있음)
    public User 로그인(User user) {
        //  userRepository에 해당 기능이 없어서 따로 만들어줘야 함.
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());


    }
*/

}
