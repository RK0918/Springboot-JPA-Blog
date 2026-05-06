package com.example.my_blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
// org.springframework.data.annotation.Id (Spring Data 범용)
// 위 라이브러리 같은 경우는, NoSQL 혹은 JPA가 아닌 방식에서 쓰임 주의하자


@Data // getter/setter가 없으므로
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Builder // 빌더 패턴 -> 배우다 보면 알게됨
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autu_increment
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터 다룰 때  씀
    private String contet; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨.

    @ColumnDefault("0") // 여기선 문자열이 아닌 숫자이기 때문에 "''" 가 아니라 그냥 ""
    private int count; // 조회수


    @ManyToOne // => Many = Board, one = User (여래 개의 게시글은 한 명의 유저에 의해 쓰일 수 있다는 뜻, 마찬가지로 응용하면 Many = one, User = one => 연관관계가 만들어지는 것
    @JoinColumn(name ="userId") // 실제로 데이터가 만들어질 땐 userId로 만들어질거다란 뜻 => 근데 userId라고 그냥 들어가면 연관관계가 없으니 연관관계를 맺어줘야됨. => ManyToOne
    private User userId;
    // userId값을 통해서 다시 user를 select 하거나
    // 아니면 userId값과 user와 board의 id를  join해서 데이터를 가져올 수 있으나
    // jpa에선 key값을 이용하지 않고 userId를 바로 넣음

    // DB는 오브젝트를 저장할 수 없다. 따라서 FK를 이용하는데, java는 오브젝트를 저장할 수 있다.
    // 그렇기 때문에 java가 db에 맞춰서 Fk를 저장하는데
    // jpa(orm)에선 그대로 사용할 수 있음.

    @CreationTimestamp
    private Timestamp createDate; // Timestamp(security가 아니라 sql임 주의)

}

