package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.handler.GlobalExceptionHandler;
import com.blogproject.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
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

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController: save() 호출");
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/user")
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.update(user); // 여기서는 트랜잭션이 종료되어 DB 값은 변경 but, 세션값은 변경되지 않은 상태
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * 세션 변경하기
     * 1. User Password Authentication Token 생성
     * 2. SecurityContextHolder에 Token으로 다시 만든 Authentication 넣기
     *     Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
     *     SecurityContext securityContext = SecurityContextHolder.getContext();
     *     securityContext.setAuthentication(authentication);
     *     session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
     */
//     전통적인 로그인 방법
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//        System.out.println("UserApiController: login() 호출");
//        User principalUser = userService.login(user);
//        if (principalUser != null) {
//            session.setAttribute("principalUser", principalUser);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }
}
