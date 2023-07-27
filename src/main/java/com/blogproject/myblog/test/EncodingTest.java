package com.blogproject.myblog.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodingTest {

    public void passwordHash() {
        String encPassword = new BCryptPasswordEncoder().encode("1234");
        System.out.println("1234 해쉬 함수 적용 : " + encPassword);
    }
}
