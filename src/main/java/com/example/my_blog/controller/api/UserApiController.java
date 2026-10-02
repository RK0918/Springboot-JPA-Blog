package com.example.my_blog.controller.api;


import com.example.my_blog.config.auth.PrincipalDetail;
import com.example.my_blog.dto.ResponseDto;
import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.eclipse.tags.shaded.org.apache.regexp.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 여긴 나중에 어플로도 사용 가능하기 때문에 data만 리턴할거기 때문에
// @RestController
@RestController
public class UserApiController {


    @Autowired
    private UserService userService;
    // 회원수정 시 session 변경을 위해 spring security에서
    // 먼저 AuthenticaitonManager 를 생성하여 bean에 등록
    // 이후 UserService에서 di하여 사용, 이후 아래 회원수정()메서드에서 세션등록
    @Autowired
    private AuthenticationManager authenticationManager;


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
        user.setRole(RoleType.USER);
        userService.회원가입(user);
        // status -> OK 보다는 value()로 설정하  여 200(성공), 500(실퍠) 상태값을 보여주는게 좋음
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        // user.js에서 done(fucntion(resp){})에서 위 코드에 다라서
        // resp에 HttpStatus.OK, 1 이 반환됨.


        // 2. 로그인 api 는 필요하지 않나요? -> 스프링 시큐리티에서 로그인 세션을 가로챔
        // 따라서 SecurityConfig -> loginProcessingUrl() 참고
    }

    @PutMapping("/user")
    // @RequestBody로 받아야 json을 받을 수 있음.
    // 아니라면 key=value 형태로 받을 수 있고, x-www-form-urlencoded 매개변수 받기
    // 1. 세션값 변경을 위해 @AuthenticationPrincipal, HttpSession 매개변수 받기
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.회원수정(user); // 이제 userService에서 회원수정 기능을 만듦
        // 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
        // 그러나 세션값은 변경되지 않았기 때문에 우리가 직접 세션값을 변경해줘야 됨.
  
        // 세션 등록(Authenticaiton 객체가 만들어지면서 등록)
        // userService에서 세션등록을 하려 했으나 그러면 db에 등록되기 전에 전에 로그인요청을 하는 것이기 때문에
        // 그럴 수 없음 -> userApiController에서 세션등록을 하는 것이 맞음.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

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
