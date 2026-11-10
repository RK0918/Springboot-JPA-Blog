package com.example.my_blog.test;

import com.example.my_blog.model.Board;
import com.example.my_blog.model.Reply;
import com.example.my_blog.repository.BoardRepository;
import com.example.my_blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id) {
        return boardRepository.findById(id).get();
        // 실행되면서 jackson 라이브러리가 오브젝트를 json으로 리턴 -> 모델의 getter를 호출
        // 무한참조가 발생함( replys가 문제 )
    }

    @GetMapping("/test/reply")
    public List<Reply> getBody() {
        return replyRepository.findAll();
        // 실행되면서 jackson 라이브러리가 오브젝트를 json으로 리턴 -> 모델의 getter를 호출
        // 무한참조가 발생함( replys가 문제 )
    }
}
