package com.example.my_blog.service;


import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {

        User user = userRepository.findByUsername(username).orElseGet(() -> {
            // 여기서는 만약 회원을 못찾았을 경우 빈객체()로 반환했지만
            // new User("fase", "FADs") 이렇게 객체를 보낼 수도 있음
            return new User();


        });
        // null 이 될 수도 있으므로 그냥 get()인데 지금은 orElseGet()이므로 null값이 아닌
        // 빈 객체가 반환됨 (만약 usernanme을 찾았을 때 없다면)
        return user;
    }

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

        // Validate 체크 ( if문 -> Ouath값이 kakao가 아니라서 null이거나 비어있으면 회원수정 x 패스워드 고정 )
        // updateForm에선 물리적으로 수정못하게 했지만 postman이나 post를 통해 공격이 가능하기
        // 때문에 아래 코드로 체크해주는 것이 중요
        if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword(); // 1234 원문
            String encPassword = encoder.encode(rawPassword); // 해쉬
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }
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
