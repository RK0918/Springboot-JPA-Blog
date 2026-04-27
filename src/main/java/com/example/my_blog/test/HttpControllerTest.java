package com.example.my_blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

//사용자가 요청 -> 응답(Data) 해주는 컨트롤러
// @RestController -> 문자 그대로를 return 해줌.
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest :" ;


    // lombok 라이브러리( getter/setter & 생성자 ) test

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = new Member(1, "ssar", "1234", "email");
        System.out.println(TAG + " getter : " + m.getId());
        m.setId(5000);
        System.out.println(TAG + "setter " + m.getId());
        // 1. @AllArgsConstructor // 전체 생성자
        // -> Member m1 = new Member(id, username, password, email);
        // 2. @NoArgsConstructor // 빈 생성자
        // -> Member m1 = new Member();


        return "lombok test 완료";
    }

    // 인터넷 브라우저 요청은 무조건 -> get 요청밖에 할 수 없다(+쿼리 스트링도)
    // http://localhost:8080/http/get (insert)
    @GetMapping("/http/get")
    // Object인 Member를 매개변수로 받을 경우 @RequsetParam 없이 get메소드를 통해 get요청을 보낼 수 있다.
    public String getTest(Member m) { // ?id=1&username=ssar&password=1234&email=ssar@nate.com
        return "get 요청 : " + m.getId() +"," + m.getUsername() + "," + m.getPassword() +"," + m.getEmail();
    }
    /*public String getTest(@RequestParam int id, @RequestParam String username) {
        // -> @RequestParam int id -> 쿼리파라미터 ~~~/http/get?id=1
        return "get 요청 :" +id + "," + username;
    }*/

    // http://localhost:8080/http/post (insert)
    // post는 get처럼 url에 ?뒤에 쿼리스트링으로 데이터를 보내는 게 아니라
    // Body에 담아 보내느 것임.(postman에서 Body를 눌러서 진행)
    // post는 1. form 2. raw 2가지 방법이 있음



    /* 1. form : x-www-form-urlencoded ->
    @PostMapping("/http/post")
    public String postTest(Member m) {
        return "post 요청"+ m.getId() +"," + m.getUsername() + "," + m.getPassword() +"," + m.getEmail();
    }
    */
    /*
    2. raw : text -> Body에 text를 적어 보냄.
    @PostMapping("/http/post")
    public String postTest(@RequestBody String text) {
        return "post 요청 : " + text;
    }
    */

    // 3. raw : JSON -> Body에 json 형태의 데이터를 보냄.
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) { // MessageConverter (스프링부트)
        return "post 요청"+ m.getId() +"," + m.getUsername() + "," + m.getPassword() +"," + m.getEmail();
    }


    // http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청" + m.getId() +"," + m.getUsername() + "," + m.getPassword() +"," + m.getEmail();
    }

    // http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String delelteTest() {
        return "delete 요청";
    }

}
