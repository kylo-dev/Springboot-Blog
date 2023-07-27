package com.blogproject.myblog.service;

import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.entity.UserRole;
import com.blogproject.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void save(User user){
        validationDuplicateUser(user);
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
        List<User> userList = userRepository.findAll();
        return userList;
    }

    /**
     * 중복 회원 검사
     * @Param username
     */
    private void validationDuplicateUser(User user){
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException();
        }
    }

    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
