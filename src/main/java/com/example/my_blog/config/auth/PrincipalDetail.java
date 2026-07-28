package com.example.my_blog.config.auth;

import com.example.my_blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//  스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행, 완료되면 UserDetails 타입의 오브젝트
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails {
    private User user; // 콤포지션(-> 객체를 품고있음 )

    // PrincipalDetailService 클래스에서 new PrincipalDetail() 을 반환해야되는데
    // 없으므로 생성자를 만들어줌
    public PrincipalDetail(User user) {
        this.user = user;
    }

    // 계정이 갖고있는 권한 목록을 리턴한다. ( 권한이 여러 개 있을 수 있어서 루프를 돌아야 하는데 우리는 한 개만 할 것)
    // extends GrantedAuthority-> 상속한 Collection 타입이어야 됨. 까다롭다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 익명클래스가 만들어지고 거기에 추상메서드가 오버라이딩,
        // java는 오브젝트는 넘길 수 있지만 메서드는 넣을 수 없기 때문에 그렇다.
        Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList는 Collection타입 중 하나
/*
        collectors.add(new GrantedAuthority() { // <- 익명클래스(오브젝트)
            @Override
            public String getAuthority() { // <-함수 추상메서드가 오버라이딩
                return "ROLE_" + user.getRole(); // ROLE_USER -> 이렇게 돼야 확인됨
                // Role을 받을 때 꼭 스프링에선 "ROLE_" 를 붙여야됨
            }
        });
*/
        // 위 코드에서 어차피 GrantedAutohrity() -> 하나의 메서드( getAuthority() ) 만 가지고
        // 있으며, 위 타입만을 가져야 하므로 () -> 가 가능
        collectors.add(() -> {
                    return "ROLE_" + user.getRole();
                }
        );

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    // 계정이 만료되지 않았는지 리턴 (true 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // 계정이 잠겨있지 않았는지 리턴 (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // 비밀번호가 만료되지 않았는지 리턴 (true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // 계정 활성화가 되어있는지 리턴( true: 활성화)
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
