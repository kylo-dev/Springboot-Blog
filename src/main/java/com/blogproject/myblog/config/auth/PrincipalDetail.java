package com.blogproject.myblog.config.auth;

import com.blogproject.myblog.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 스프링 시큐리티로 로그인을 진행하고 완료되면 UserDetails 타입의 오브젝트를
 * 스프링 시큐리티의 고유한 세션 저장소에 저장한다.
 */
@Data
public class PrincipalDetail implements UserDetails {

    private User user; // composition

    public PrincipalDetail(User user) {
        System.out.println("PrincipalDetail 생성자 : 호출");
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 확인 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지 확인 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 확인 (true: 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화가 됐는지 확인 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 권한을 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();

//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });
        collectors.add(()->{return "ROLE_"+user.getRole();});
        return collectors;
    }
}
