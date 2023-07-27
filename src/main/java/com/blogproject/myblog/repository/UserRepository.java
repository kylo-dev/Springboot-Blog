package com.blogproject.myblog.repository;

import com.blogproject.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * DAO
 * 자동으로 Bean 등록
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // select * from user where username = ? AND password = ?;
    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM user WHERE usrename = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}
