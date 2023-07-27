package com.blogproject.myblog.test;

import org.springframework.web.bind.annotation.*;

/**
 * 사용자 요청 -> 응답 (HTML) : @Controller
 *
 * 사용자 요청 -> 응답 (DATA) : @RestController
 */

@RestController
public class HttpController {
    @GetMapping("/http/get")
    public String getTest(Member member){
        return "get 요청 : " + member.getId() + ", " + member.getUserName() + ", " + member.getPassword() + ", " + member.getEmail();
    }

    /**
     * Json 방식은 @RequestBody Member member(받을 클래스) 로 데이터를 전달받음
     * MessageConverter가 자동으로 매핑해줌
     */
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member member){
        return "post 요청 : " + member.getId() + ", " + member.getUserName() + ", " + member.getPassword() + ", " + member.getEmail();
    }

    @PutMapping("/http/put")
    public String putTest(@RequestBody Member member){
        return "put 요청 : " + member.getId() + ", " + member.getUserName() + ", " + member.getPassword() + ", " + member.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }

}
