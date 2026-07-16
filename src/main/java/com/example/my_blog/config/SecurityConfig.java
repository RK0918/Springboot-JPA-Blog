package com.example.my_blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig {

    @Bean // Ioc가 됨.
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// csrf 토큰 비활성화 (테스트시 걸어서 토큰없이도 통과되도록 함)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/").permitAll()

                        .requestMatchers("/js/**", "/css/**", "/image/**").permitAll()
                        .requestMatchers("/WEB-INF/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 아래는 위와 다르게 인증이 되지 않은 페이지는 아래로
                .formLogin(form -> form
                        .loginPage("/auth/loginForm")
                        .loginProcessingUrl("/auth/loginProc")
                        .defaultSuccessUrl("/") // 스프링 시큐리티가 해당 주소로 요청이 오는 로그인을 가로채서 대신 로그인
                );
        return http.build();
    }
}