package com.example.my_blog.config.auth;

import com.example.my_blog.model.User;
import com.example.my_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 아래 loadUserByUsername()를 꼭 오버라이딩해서 만들어줘야 
    // 우리가 커스텀한 user 정보를 담아서 저장할 수 있다. 그래서 따로
    // 아래 메서드를 만들어줘야 되는 것

    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로챘는데
    // password 부분 처리는 알아서 함
    // username이 db에 있는지만 확인해주면 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        // userRepository.findByUsername() -> 해당 함수가 없으니
        // userRepositoy에서 따로 만들어줘야 함.
        User principal = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);

                });

        return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장됨.
        // -> PrincipalDetail 클래스에서 null이기 때문에 따로
        // PrincipalDetail 생성자를 만든다
    }


}
