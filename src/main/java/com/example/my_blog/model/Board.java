package com.example.my_blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;
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


    // ManyToOne에 의해 user 데이터는 한 건 밖에 없다 => EAGER 기법 사용(그냥 무조건 가져와라, Board(게시물)에선 무조건 user(작성자)가 필요하기 때문에 EAGER)
    @ManyToOne(fetch = FetchType.EAGER) // => Many = Board, one = User (여래 개의 게시글은 한 명의 유저에 의해 쓰일 수 있다는 뜻, 마찬가지로 응용하면 Many = one, User = one => 연관관계가 만들어지는 것
    @JoinColumn(name ="userId") // 실제로 데이터가 만들어질 땐 userId로 만들어질거다란 뜻 => 근데 userId라고 그냥 들어가면 연관관계가 없으니 연관관계를 맺어줘야됨. => ManyToOne
    private User userId;
    // userId값을 통해서 다시 user를 select 하거나
    // 아니면 userId값과 user와 board의 id를  join해서 데이터를 가져올 수 있으나
    // jpa에선 key값을 이용하지 않고 userId를 바로 넣음

    // DB는 오브젝트를 저장할 수 없다. 따라서 FK를 이용하는데, java는 오브젝트를 저장할 수 있다.
    // 그렇기 때문에 java가 db에 맞춰서 Fk를 저장하는데
    // jpa(orm)에선 그대로 사용할 수 있음.

    // JAP에서 board -> user의 오브젝트가 board 클래스에 있기 때문에
    // 알아서 board + user 두 가지를 join해서 다시 반환해줌 .
    // 마찬가지로 아래 reply 오브젝트의 정보를 board 클래스에 추가하여
    // board + user + reply 를 join하여 반환(별도의 쿼리문을 작성하거나 반환할 필요 없이
    // 여기서 주의할 점은 reply의 경우 하나의 유저가 하나의 게시물을 작성하나
    // reply는 여러 개가 존재할 수 있으므로 List<> 타입으로 설정


    // 아래 (mappedBy = "board")는 reply 클래스의 private Board board << 에서 board를 적어주면 됨
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER ) // mappedBy 연관관계의 주인이 아니다. (난 FK가 아님) DB에 컬럼xx
    // @JoinColumn(name="replyId") 가 굳이 필요없음 강의, replyId가 들어오는 게 말이 안됨
    // 답변(reply)이 늘어나서 1, 2... 가 늘어나게 되면 1정규화가 깨지게 됨.(원자성x), 따라서 필요없음
    private List<Reply> reply; // reply는 연관관계의 주인이 아님. board를 셀렉트 할 때 join문을 통해 값을 얻기 위해 필요한 것
    // reply는 수십 만건이 될 수도 있으므로 들고올수도, 들고오지 않을 수도 있음 => Lazy 기법 사용
    // 그러나 ! 댓글을 펼치기나, 다른 팝업을 통해 보는 게 아니고 게시물을 눌러서 상세보기를 통해 한 꺼번에 볼 경우
    // -> 다시 EAGER 기법을 쓰는 것이 맞다.

    @CreationTimestamp
    private Timestamp createDate; // Timestamp(security가 아니라 sql임 주의)

}
