package com.example.my_blog.controller.api;


import com.example.my_blog.config.auth.PrincipalDetail;
import com.example.my_blog.dto.ResponseDto;
import com.example.my_blog.model.Board;
import com.example.my_blog.model.User;
import com.example.my_blog.service.BoardService;
import com.example.my_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// 여긴 나중에 어플로도 사용 가능하기 때문에 data만 리턴할거기 때문에
// @RestController
@RestController
public class BoardApiController {


    // BoardService에서 user 정보도 다뤄야 하는데 이를 위해서
    // board 와 user 객체를 담고 있는 principal 두 가지를 객체로 넘겨줘야됨. (아래 save() 참고)
    // 근데 principal을 꺼내 쓰려면 @Getter가 필요 -> principalDetail로 넘어감
    @Autowired
    BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        // board ( title, content ) + principal에서 getUser()을 통해 user정보도 가져옴(글을 누가 썼는지 알아야 함)
        boardService.글쓰기(board, principal.getUser()); // -> 글쓰기()
        // BoardService에선 User user를 받으면 됨.
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        // 응답을 할 것이고 baord.js에서 응답이 잘되면 done(function(resp)), 되지 않으면 fail(....)
    }


    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);

    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {


        boardService.글수정하기(id, board);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);


    }

















/*    // 전통적인 방식의 로그인 방식
    @PostMapping("/api/user/login")
    // @Autowired 없이 login(@RequestBody User user, HttpSession session) 도 가능
    public ResponseDto<Integer> login(@RequestBody User user) {
        System.out.println("UserApiController : login 호출");
        User principal = userService.로그인(user); // 로그인 기능 만들러 가야됨.


        // 아닐 경우 세션을 만들어주면 되는데 -> 스프링에서 위 함수에 매개변수로 session을 받을 수 있음
        if (principal != null) {
            session.setAttribute("principal", principal);
        }

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
*/

}
