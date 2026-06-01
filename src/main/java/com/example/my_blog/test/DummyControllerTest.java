package com.example.my_blog.test;

import com.example.my_blog.model.RoleType;
import com.example.my_blog.model.User;
import com.example.my_blog.repository.UserRepository;
import jakarta.persistence.EnumType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;


// html파일이 아니라 data를 return해주는 controller = @RestController
@RestController
public class DummyControllerTest {

    @Autowired // 의존성주입 (DI)
    private UserRepository userRepository;

    // 4. Delete
    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {


        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 유저 없음"));

        userRepository.delete(user);

        return "삭제되었습니다.";
    }

    // 3. put(업데이트)

    // save 함수는 id를 전달하지 않으면 insert를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert 해줌.


    // 수정할 때, id와 username은 수정안할 것
    // email, password 수정
    
    @Transactional // 함수 종료시에 자동 commit이 됨.ㄴ save() 주석처리됐음에도 업데이트가 됨.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // @RequestBody 어노테이션 -> json 데이터를 요청 =>  Java Object(MessageConvert의 Jackson 라이브러이에서 라이브러리가 변환해서 받아줌.
        // // @RequestBody를 통해서 josn 형태로 데이터를 받음.
        System.out.println("id : " + id );
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : "  + requestUser.getEmail());
        // 그냥 데이터를 set() 해버리고 save()를 해버리면 insert때는 괜찮지만 update는 수정되는 값 이외에
        // username, role 등의 값들이 null이 나오면서 오류발생
        // 따라서 findById()를 통해 데이터 조회 -> user 변수에 id값에 해당하는 user 데이터를 담고
        // get() -> set() -> save()
        // null값이 아닌 전체 데이터에서 password와 email이 수정(update)됨.

        User user = userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        }); // id를 조회하고 셀렉트하는 이 때, 영속화가 된다. (영속성 컨텍스트)
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        // 영속화만 된 상태에서 위 두 코드로 password, email이 변경
        // @Transactional 어노테이션 덕분에 영속성 컨텍스트 1차캐시에 저장된 user 데이터와
        // 변경된 user오브젝트와 비교하여 변경되었으면 커밋되면서 user db에 데이터를 update해줌
        // 변경된 점이 없다면 영속화만 됨. '변경 감지'가 일어나면 update(더티 체킹)


        // userRepository.save(user);

        // 영속성 컨텍스트 & 플러시 & 더티 체킹
        return user;
    }

    // 2. select & paging test

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();

    }
    // 한 페이지당 2건의 데이터를 리턴받아 볼 예정 ( size(2건씩), sort=정렬기준, direction= 정렬을 어떤식으로(id를 DESC(최신순)))
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        // jpa
        Page<User> pagingUser =  userRepository.findAll(pageable);

        // if(pagingUser.isLast()) 나 여러 메소드를 이용하여 분기처리도 가능
        // 노션에 정리해놓음

        List<User> users = pagingUser.getContent();

        return users;
    }



    // 1. insert

    // 회원가입을 할 때 user 오브젝트(클래스)에서
    // id(IDENTITY), role, creatDate는 전략이나 디폴트값으로 자동
    // 으로 값이 생성되기 때문에 User의 다른값만 받으면 됨.
    // => username, password, email


    // 나중에 id에 해당하는 값이 없으면 다른 페이지를 열리게 하여 -> AOP 이용 ( 나중에 배움 )
    // {id} 주소로 파라미터를 전달받을 수 있음.
    // http://localhost:8000/blog/dummy/user/3 => 여기서 3이 {id} 값으로 들어감.
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // user /4을 찾을때 데이터베이스에서 못찾을 경우 user가 null이 될 수 있음-> 그럼 return null이 반횐되고 프로그램에 문제
        // ->Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해! 라는 뜻.

        // 람다식(인터페이스를 new해서 쓰고 있기 때문에 가능)
        /*
        User user = userRepository.findByID(id).orElseThorw(() -> {
            return enw IllegalArgumentException("해당 사용자는 없습니다");
        }); -> 예외처리를 위해 예외나 오류를 찾을 필요없이 편하게 사용 가능
         */

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            // 위 코드에서 그냥 .get()을 쓰면 orElseThrow는 예외처리를 통해 메시지 전달했지만 .get()은 그런거 없음
            // 위 코드에서 .orElseGet()을 쓸경우 없는 id값이기 때문에 각각의 값을 모두 null(혹은 0)을 return

            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. ");
            }
        }); // findById가 return하는 값은 상속되는 인터페이스에 따라 Optional<T> 이다.

        // 요청 : 웹브라우저에서 함
        // user 객체 = 자바 오브젝트

        // 웹브라우저에서 요청했으므로 html를 이해할 수 있고
        // user객체(오브젝트)는 이해하기 힘듦
        // 따라서 변환을 해줘야 됨.
        // 변환 ( 웹브라우저가 이해할 수 있는 데이터 ) -> Json (Gson 라이브러리)
        // 스프링부트에선 MessageConvert 라는 것이 응답시에 자동작동
        // 만약 자바 오브젝트를 리턴하게 되면 메시지컨버트를 통해
        // Jackson 라이브러리를 호출해서 user 오브젝트 -> Json으로 변화하여
        // 브라우저에게 던져줌.
        return user;
        // 그래서 return user가
        // {"id":3,"username":"cos","password":"1234","email":"cos@gmail.com","role":"USER","createDate":"2026-05-15T10:06:42.387+00:00"}
        // http://localhost:8000/blog/dummy/user/3를 들어가면 위 형태의 json으로 출력됨
        // 위 페이지에서 응답헤더에서 content-type을 보면
        // -> application/json;charset=UTF-8
        // http 헤더에서 json 형태로 응답된걸 볼 수 있음.
    }

    /*
    @PostMapping("/dummy/join")
    public String join(String username, String password, String email) { // 매개변수에 변수명만 적으면 저 파라미터 3개는 파싱되어 key-value값으로 받아진다. ( spring에서 제공되는 약속된 규칙임, )
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("email : " + email);

        return "회원가입 완료";
    }
    */

    // 위의 방식으로 매개변수 3개로도 가능하지만 아래처럼 오브젝트를 매개변수로 받아 처리하는 게 가능해지는 강력한 기능


    @PostMapping("/dummy/join")
    public String join(User user) { // 매개변수에 변수명만 적으면 저 파라미터 3개는 파싱되어 key-value값으로 받아진다. ( spring에서 제공되는 약속된 규칙임, )
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("id : " + user.getId());
        System.out.println("role : " + user.getRole());
        System.out.println("createDate : " + user.getCreateDate());


        // 그냥 "user"를 사용할 경우 user2 처럼 잘못넣을 가능성 존재
        // -> Enum(RoleType)을 사용해 타입 강제, Enum 생성
        // -> User 클래스에서 @Enumerated(EnumType.STRING) 적용
        user.setRole(RoleType.USER);
        // role은 null값으로 insert 되므로, 따로 setRole(user)로 넣어주는데
        // 이 값을 Enum값으로 타입을 강제시킨다 이해하면 편함

        userRepository.save(user); // UserReopository
        return "회원가입 완료";
    }


}
