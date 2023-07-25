package com.blogproject.myblog.controller;

import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.repository.UserRepository;
import com.blogproject.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
public class DummyController {

    private final UserService userService;
    private final UserRepository userRepository;

    // http://localhost:8082/blog/dummy/join (요청)
    // http의 body에 username, password, email, role 데이터를 가지고 (요청)
//    @PostMapping("/dummy/join")
//    public String join(@RequestParam("username") String username,
//                       @RequestParam("password") String password,
//                       @RequestParam("email") String email,
//                       @RequestParam("role") UserRole role) {
//        System.out.println("username = " + username + ", password = " + password + ", email = " + email + ", role = " + role);
//        return "회원가입이 완료 되었습니다.";
//    }

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("user의 이름 = " + user.getUsername() + " user의 비밀번호 = " + user.getPassword() +
                " user의 이메일 = " + user.getEmail() + " user의 역할 = " + user.getRole());
        userService.save(user);
        return "회원가입이 완료 되었습니다.";
    }

    // {id} 주소로 파라미터를 전달 받을 수 있음
    // http://localhost:8082/blog/dummy/user/1
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자가 없습니다.");
            }
        });
        return user;
    }

    @GetMapping("/dummy/user")
    public List<User> list() {
        return userRepository.findAll();
    }
}
