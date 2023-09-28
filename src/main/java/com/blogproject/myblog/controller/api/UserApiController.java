package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.dto.Result;
import com.blogproject.myblog.dto.UpdateUserResponseDto;
import com.blogproject.myblog.dto.UserDto;
import com.blogproject.myblog.entity.KakaoProfile;
import com.blogproject.myblog.entity.OAuthToken;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.handler.GlobalExceptionHandler;
import com.blogproject.myblog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 회원가입 요청
    @PostMapping("/auth/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController: save() 호출");
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 회원정보 수정 요청
    @PutMapping("/api/user")
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.update(user); // 여기서는 트랜잭션이 종료되어 DB 값은 변경. but, 세션값은 변경되지 않은 상태
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 회원 수정 API
    @PutMapping("/api/users/{id}")
    public UpdateUserResponseDto updateV2(@PathVariable("id") Long id, @RequestBody User user) {
        userService.update(user);
        User findUser = userService.findById(id);
        return new UpdateUserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());
    }

    // 모든 회원 조회 API
    @GetMapping("/api/users")
    public Result findUsers() {
        List<User> findUsers = userService.findAll();

        List<UserDto> result = findUsers.stream()
                .map(u -> new UserDto(u.getUsername(), u.getEmail(), u.getRole()))
                .collect(Collectors.toList());
        return new Result(result, result.size());
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
