package com.example.my_blog.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Reply {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length= 200)
    private String content;

    // 이걸 누가, 어느 테이블에? 있는 답변인가? -> 연관관계 맺어줄 것임
    // 여러개의 답변은 하나의 게시물에 -> ManyToOne
    // 하나의 답변에 하나의 게시물 -> OneToOne
    // 하나의 답변에 여러개의 게시물 -> OneToMany -> ?? 무슨
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board borad;

    // 이 답변을 누가 했는지도 알아야됨
    @ManyToOne
    @JoinColumn(name ="userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
