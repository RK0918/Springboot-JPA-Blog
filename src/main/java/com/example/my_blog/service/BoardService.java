package com.example.my_blog.service;


import com.example.my_blog.model.Board;
import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록, IoC를 해줌
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;


    @Transactional
    public void 글쓰기(Board board, User user) { // title, content
        // UserService 를 복사해서 만듦.
        // model에서 Board를 확인하여 데이터를 뭘 다루는지 확인

        // 유저 데이터도 같이 다뤄야 하기 때문에 -> BoardApiController로 가서
        board.setCount(0);  // 조회수는 0으로 초기설정
        board.setUser(user);
        boardRepository.save(board);


    }

    // BoardController에서 @Pageable ~~ 이 더 들어가지고 이걸 받기 위해 List<Board> -> Page<Board>로 반환 타입변경
    // 글목록을 보는데 왜 List<Board>가 아니라 Page<Board>를 반환받음?
    // => dummyControllerTest에서 pageable을 테스트하면 알 수 있지만
    // 기존에선 우리가 설정한 board 정보(content)(만 나오지만 추가적으로
    // Pageable 정보를 통해 첫글인지 마지막글인지 알 수 있는 "last(true/false)" 정보를 가져올 수 있음
    // 이 last 정보를 통해 첫글인지 마지막인지 파악하여 토글버튼을 활성화/비활성화 가능
    @Transactional(readOnly = true) // select만 하므로
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable); // 반환타입을 Page<Board>
    }


    // Optional 반환함
    @Transactional(readOnly = true) // select만 하므로
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패: 아이디 찾이 못함");
                });
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }


    @Transactional
    public void 글수정하기(int id, Board requestBoard) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 찾기 실패: 아이디 찾이 못함");
                }); // 영속화 완료

        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 1. 해당 함수 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다.
        // 2. 이때 더티체킹이 일어남(영속화되어 있던 board 데이터가 달라졌기 때문에)
        // 3. 자동으로 업데이트가 됨 , DB flush

    }

/* 전통적인 로그인방식
    @Transactional(readOnly = true) // Select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지시킬 수 있음)
    public User 로그인(User user) {
        //  userRepository에 해당 기능이 없어서 따로 만들어줘야 함.
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());


    }
*/

}
