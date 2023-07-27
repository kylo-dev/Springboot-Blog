package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.handler.GlobalExceptionHandler;
import com.blogproject.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/auth/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController: save() 호출");
        userService.save(user);
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
