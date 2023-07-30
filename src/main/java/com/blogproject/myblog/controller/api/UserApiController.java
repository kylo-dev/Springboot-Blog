package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.entity.KakaoProfile;
import com.blogproject.myblog.entity.OAuthToken;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.handler.GlobalExceptionHandler;
import com.blogproject.myblog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


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
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

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
