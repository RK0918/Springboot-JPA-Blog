package com.example.my_blog.model;


import com.example.my_blog.dto.ReplySaveRequestDto;
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

    @Column(nullable = false, length = 200)
    private String content;

    // 이걸 누가, 어느 테이블에? 있는 답변인가? -> 연관관계 맺어줄 것임
    // 여러개의 답변은 하나의 게시물에 -> ManyToOne
    // 하나의 답변에 하나의 게시물 -> OneToOne
    // 하나의 답변에 여러개의 게시물 -> OneToMany -> ?? 무슨
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board; //


    // 이 답변을 누가 했는지도 알아야됨
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", board=" + board +
                ", user=" + user +
                ", createDate=" + createDate +
                '}';
    }

    /*  dto 이용 시, 아래 함수를 통해 builder패턴 말고도 가능하단 것만 확인하자
  // dto 사용을 위해, ReplySaveRequestDto를 만들었고
  // boardService 댓글쓰기에서 영속화 후 reply 객체를 만들 때
  // 일일이 user, board, content를 받기 힘드니 함수로 생성하여 사용
    public void update(User user, Board board, String content) {
        setUser(user);
        setBoard(board);
        setContent(content);
    }*/


}
