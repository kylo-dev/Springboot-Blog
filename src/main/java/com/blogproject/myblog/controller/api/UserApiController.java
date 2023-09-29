package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.dto.Result;
import com.blogproject.myblog.dto.user.JoinUserDto;
import com.blogproject.myblog.dto.user.UpdateUserRequest;
import com.blogproject.myblog.dto.user.UpdateUserResponseDto;
import com.blogproject.myblog.dto.user.UserDto;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 회원가입 요청
    @PostMapping("/auth/api/user")
    public ResponseDto<Integer> save(@RequestBody JoinUserDto joinUserDto) {
        System.out.println("UserApiController: save() 호출");
        User user = User.builder()
                        .username(joinUserDto.getUsername())
                        .password(joinUserDto.getPassword())
                        .email(joinUserDto.getEmail())
                        .build();
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 회원정보 수정 V1
    @PutMapping("/api/user")
    public ResponseDto<Integer> update(@RequestBody UpdateUserRequest request) {
        User user = User.builder()
                        .id(request.getId())
                        .username(request.getUsername())
                        .password(request.getPassword())
                        .email(request.getEmail())
                        .build();
        userService.update(user); // 여기서는 트랜잭션이 종료되어 DB 값은 변경. but, 세션값은 변경되지 않은 상태
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 회원정보 수정 API V2
    @PutMapping("/api/users/{id}")
    public UpdateUserResponseDto updateV2(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) {
        User user = User.builder()
                .id(id)
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
        userService.update(user);
        return new UpdateUserResponseDto(id, request.getUsername(), request.getEmail());
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

    @DeleteMapping("/api/users/{id}")
    public ResponseDto<Integer> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            return new ResponseDto<Integer>(HttpStatus.BAD_REQUEST.value(), 0);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


//     일반적인 로그인 방법 (Security 사용 X)
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
