package com.example.my_blog.repository;

import com.example.my_blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// jsp로 치면 -> DAO (Data Access Object)
// bean으로 등록이 되나요 ? -> 스프링에서 ioc에서 객체로 가지고 있나요 ?
// -> 자동으로 bean등록이 된다. -> @Repository -> 해당 어노테이션이 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {


    // PrincipalDetailService 클래스 때문에 따로 만들어주는 함수
    // SELECT * FROM user WHERE username =1?; <- 이것도 네이밍쿼리
    Optional<User> findByUsername(String username);
}


// DummyControlle 클래스로 돌아가기

// 여기도 전통적인 로그인 방식할 때

// 로그인을 위한 함수
// JPA Naming 쿼리 전략
// 1. 아래 함수는 findBy~~(뒤에 붙인 Username -> username,  Passowrd-> password 로
// SELECT * FROM User WHERE username - ?1 AND password = ?2; 으로 취급됨

// User findByUsernameAndPassword(String username, String password);

// 2. 위와 같이 작동함. 아래는 네이티브쿼리로 작성 일단 코드가 간단하므로 위를 사용
// @Query(value = "SELECT * FROM User WHERE username - ?1 AND password = ?2", nativeQuery = true)
// User login(String username, String password);


