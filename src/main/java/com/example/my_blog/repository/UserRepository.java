package com.example.my_blog.repository;

import com.example.my_blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// jsp로 치면 -> DAO (Data Access Object)
// bean으로 등록이 되나요 ? -> 스프링에서 ioc에서 객체로 가지고 있나요 ?
// -> 자동으로 bean등록이 된다. -> @Repository -> 해당 어노테이션이 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // DummyControlle 클래스로 돌아가기

}


