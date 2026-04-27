package com.example.my_blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

// ORM -> java(다른언어 포함) Object -> 테이블로 매핑해주는 기술
@Entity // 테이블을 만드는 어노테이션, User클래스가 MySQL에 테이블이 생성된다.
public class User {

    // 프로젝트에서 연결된 DB(IDENTITY)의 넘버링 '전략'을 따라간다
    // mySql -> GenerationType.TABLE => 테이블에 번호를, AUTO일 경우 자동
    // 오라클 -> GenerationType.SEQUENCE
    // 여기선 MYSQL을 사용하므로 IDENTITY 에서 테이블 번호를 따라가는
    // TABLE 방식을 따르게 된다.
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id; // 시퀀스(오라틀), auto-increment(mysql)
    // 따라서 위 방식에 따라 테이블에 insert 할 때 id값은 비워놔도 자동으로 들어감

    @Column(nullable = false, length = 30)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @ColumnDefault("'user'") // ' ' 주의회원가입할 때 디폴트값을 줄 수 있음 => user
    private String role; // String 타입말고 Enum을 써도 좋다. // admin, user, manager ( 도메인(역할)을 정하여 권한 부여)

    @CreationTimestamp // 시간이 자동입력
    private Timestamp createDate; // 회원 내용을 수정하는 updateDate도 필요하긴 한데 일단 이것만
    // 마찬가지로 id값과 동일하게 자동으로 입력되어 값을 비워놔도 자동으로 들어감


}
