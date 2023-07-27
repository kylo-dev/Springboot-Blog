package com.blogproject.myblog.test;

import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.repository.UserRepository;
import com.blogproject.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.EmptyStackException;
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

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건에 데이터를 리턴 받기
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }

    /**
     * password와 email만 수정하는 api
     * save 함수는 id를 전달하지 않으면 insert 쿼리 생성
     * save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update 해주고
     * id를 전달할 때 해당 id에 대한 데이터가 없으면(null) insert 해줌.
     */
    @Transactional // 함수 종료시 자동 commit
    @PutMapping("/dummy/user/{id}") // Json 데이터를 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줌) (@RequestBody 필요)
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser) {
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail()); // 영속성 컨테이너에 가져온 후 => set**()을 통해 Dirty Checking으로 업데이트

         // userRepository.save(user); // id 값을 같이 줘야 update로 인식, but null 값이 있는 경우 에러 발생 => findById()
        return user;
    }

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }
        return "삭제 처리가 되었습니다 : " + id;
    }
}
