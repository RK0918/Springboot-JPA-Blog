package com.example.my_blog.controller.api;


import com.example.my_blog.dto.ResponseDto;
import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.eclipse.tags.shaded.org.apache.regexp.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 여긴 나중에 어플로도 사용 가능하기 때문에 data만 리턴할거기 때문에
// @RestController
@RestController
public class UserApiController {


    @Autowired
    private UserService userService;


/*    // 아래  login() 함수에서 HttpSession session을 매개변수로 받아도 되지만
    // 그냥 @Autowired를 통해서 di해도 됨
    @Autowired
    private HttpSession session;*/


    // 1. 회원가입 api ( jquery(user.js) 에 의해 작동 )
    // 요청받는게 JSON이므로 @RequestBody
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");

        // 실제로 DB에 isnert하고 아래에서 return 이 되면 됨.
        userService.회원가입(user);
        // status -> OK 보다는 value()로 설정하여 200(성공), 500(실퍠) 상태값을 보여주는게 좋음
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        // user.js에서 done(fucntion(resp){})에서 위 코드에 다라서
        // resp에 HttpStatus.OK, 1 이 반환됨.


        // 2. 로그인 api 는 필요하지 않나요? -> 스프링 시큐리티에서 로그인 세션을 가로챔
        // 따라서 SecurityConfig -> loginProcessingUrl() 참고
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
