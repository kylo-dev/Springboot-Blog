package com.blogproject.myblog.controller;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // @AuthenticationPrincipal PrincipalDetail principal // 컨트롤러에서 세션 찾기
    @GetMapping("/")
    public String index(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        model.addAttribute("boards", boardService.findAll(pageable));
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

}
