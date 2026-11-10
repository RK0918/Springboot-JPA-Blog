package com.example.my_blog.repository;

import com.example.my_blog.dto.ReplySaveRequestDto;
import com.example.my_blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    // 인터페이스에선 public 생략가능

    
    // 네이티브 쿼리 작성 -> 영속화할 필요가 없어짐
    @Modifying
    @Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1,?2,?3, now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.
}
