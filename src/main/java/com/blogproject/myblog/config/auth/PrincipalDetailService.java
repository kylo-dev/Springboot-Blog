package com.blogproject.myblog.config.auth;

import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 스프링이 로그인 요청을 가로챌 때, (username, password) 변수 2개를 가로채는데
     * password 부분 처리는 알아서 처리 됨
     * username만 DB에 있는지 확인해주면 됨 - loadUserByUsername
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.: " + username);
                });
//        System.out.println("principal.getUsername() = " + principal.getUsername());
//        System.out.println("principal.getPassword() = " + principal.getPassword());
        return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장
    }
}
