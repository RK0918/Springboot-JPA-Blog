package com.example.my_blog.controller;


import com.example.my_blog.dto.ResponseDto;
import com.example.my_blog.model.KakaoProfile;
import com.example.my_blog.model.OAuthToken;
import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
public class UserController {
// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**


    @Value("${cos.key}")
    private String coskey;

    // SecurityConfig에서 Bean으로 등록해야됨.
    @Autowired
    private SecurityContextRepository securityContextRepository;

    // 카카오 로그인을 위한 기존의 userApinController에서 가져옴)
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";

    }


    // 원래는 컨트롤러에 모든 코드를 넣는 것보단
    // 컨트롤러 - 서비스 분리시켜서 하는게 좋음(이해를 위해 일단 진행)
    @GetMapping("/auth/kakao/callback")
    //원래 아래 String 앞에 @ResponseBody가 있었는데 지워줘야 view리졸버가 파일을 찾아감
    public String kakaoCallback(
            String code,
            HttpServletRequest request,
            HttpServletResponse response) { // Data를 응답(리턴)해주는 컨트롤러 함수
        // 위에서 requset, response를 추가하는 이유는 인증 정보를
        // 세션에 저장할 때 현재 HTTP 요청과 응답 객체가 필요하기 때문


        // 1.  POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // Retrofit2, OkHttp, RestTemplate 등이 있음.(요청 라이브러리)
        // RestTemplate 을 이용하면 http 요청을 쉽게 가능


        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // body 데이터를 담을 HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "6de56d59742abd59a94facdbb0f52fa1");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);


        // HttpHeade와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);


        // exchange 함수는(rt는 RestTemplate을 참조한 변수) 파라미터로
        // HttpEntity를 받는다. (KakaoTokneRequest)
        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response1 = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // url
                HttpMethod.POST, // Http Method
                kakaoTokenRequest, // HttpEntity
                String.class //  Class<T> responseType
        );

        // json 데이터를 object에 담기 위해 필요한 Gson, Json Simple, ObjectMapper(내장 라이브러리)
        // try catch 문을 해줘야 된다.
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        // json 데이터를 java에서 처리하기 위해서 java object 형태로 바꿔주는 것
        try {
            oAuthToken = objectMapper.readValue(response1.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) { // JSON 문법 오류 또는 JSON 데이터를 OAuthToken 객체로 매핑하지 못한 경우
            throw new RuntimeException(e);
        }

        System.out.println("카카오 액세스 토큰 : " + oAuthToken.getAccess_token());


        // 2. 사용자 정보 조회
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        // 사용자 정보 조회는 body가 필요없고 header가 2가지(Authorization, Content-Type)
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 사용자 정보 조회는 body가 필요없음

        // 사용자 정보 조회에선 body는 필요x Http Header만 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me", // url
                HttpMethod.POST, // Http Method
                kakaoProfileRequest, // HttpEntity
                String.class //  Class<T> responseType
        );


        System.out.println(response2.getBody());


        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        // json 데이터를 java에서 처리하기 위해서 java object 형태로 바꿔주는 것
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) { // JSON 문법 오류 또는 JSON 데이터를 OAuthToken 객체로 매핑하지 못한 경우
            throw new RuntimeException(e);
        }


        // User 오브젝트 :username, password, email
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        // 중복 안되게 카카오 유저네임은 이메일 + id 번호로 조합
        System.out.println("블로그서버 유저네임 :" + kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 이메일  :" + kakaoProfile.getKakao_account().getEmail());
        UUID garbagePassword = UUID.randomUUID(); // 임시패스워드 ( 아이디로만 로그인되지 않도록)
        // UUID란 -> 중복되지 않은 어떤 특정 값을 만들어내는 알고리즘 ->
        System.out.println("블로그서버 패스워드 :" + coskey);
        System.out.println("로그인에 성공했습니다2");

        // 위 정보 말고 별도의 회원정보가 필요할 시 회원정보 구성체(오브젝트)를 만들어서
        // 관리하는 것이 나음

        User kakaouser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(coskey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao") // 회원가입할 시 kakao(oauth)로 로그인한지 확인하기 위해 (user.java에서 oauth 속성 추가했음
                // -> updateForm에서 oauth("kakao")를 이용해 카카오로 회원가입하여 로그인시 비번 수정이 안되게 변경
                .build();
        System.out.println("로그인에 성공했습니다3");

        // 가입자 혹은 비가입자 체크 해서 처리 -> userService에서 회원찾기() 만들어줌
        User originuser = userService.회원찾기(kakaouser.getUsername());

        System.out.println("로그인에 성공했습니다4");
        // UserService 회원찾기()에서 orElseGet()을 통해 username을 찾았으므로
        // null값이 아닌 빈 객체가 반환되므로(만약 회원이 없을시)
        // originuser =null은 하지 못하고 .getUsername()을 통해 null값을 찾아줘야됨
        if (originuser.getUsername() == null) {
            System.out.println("기존 회원이 아닙니다.");
            userService.회원가입(kakaouser);
        }


        // 로그인 처리(user -> kakaouser로 수정) (기존의 userApinController에서 가져옴)
        // 기존 회원이라면 요휴 아이디값이 여기서 만들어지기 때문에
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), coskey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("로그인에 성공했습니다5");


        // 새로운 SecurityContext 객체 생성
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        // 인증에 성공한 사용자 정보를 SecurityContext에 저장
        securityContext.setAuthentication(authentication);

        // SecurityContextHolder에 인증 정보 등록
        SecurityContextHolder.setContext(securityContext);

        // 세션에 SecurityContext 저장 (Spring Security 6에서 중요)
        securityContextRepository.saveContext(securityContext, request, response);


        return "redirect:/";
    }

    // HttpHeader 오브젝트 생성

    @GetMapping("/user/updateForm")
    public String UpdateForm() {
        return "user/updateForm";
    }


}