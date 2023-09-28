package com.blogproject.myblog.service;

import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @PersistenceContext
    EntityManager em;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void save(User user){
        validationDuplicateUser(user); // 중복 회원 확인
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword()); // password 암호화
        user.setPassword(encPassword);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        } else {
            return null;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 중복 회원 검사
     */
    private void validationDuplicateUser(User user){
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("중복 회원입니다. 다른 아이디를 사용해주세요");
        }
    }

    public User validationDuplicator(User requestUser){
        User user = userRepository.findByUsername(requestUser.getUsername()).orElseGet(()->{
            return new User();
        });
        return user;
    }

    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Transactional
    public void update(User requestUser) {
        User user = userRepository.findById(requestUser.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 정보 수정 실패: 아이디를 찾을 수 없습니다.");
        });

        // validate 체크 => oauth필드에 값이 없어야 수정 가능
        if (user.getOauth() == null || user.getOauth().equals("")) {
            user.setPassword(bCryptPasswordEncoder.encode(requestUser.getPassword()));
            user.setEmail(requestUser.getEmail()); // 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 자동 실행
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
