package com.blogproject.myblog.controller;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    /**
     * 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
     * 그냥 주소가 / 이면 index.html 허용
     * static 이하에 있는 /js/**, /css/**, /image/** 허용
     */
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String update(@AuthenticationPrincipal PrincipalDetail principal, Model model) {
        model.addAttribute("principal", principal);
        return "user/updateForm";
    }
}
