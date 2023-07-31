package com.blogproject.myblog.controller;

import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.entity.Reply;
import com.blogproject.myblog.repository.BoardRepository;
import com.blogproject.myblog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final BoardRepository boardRepository;
    private final ReplyService replyService;

    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable Long id) {
        return boardRepository.findById(id).get();
    }

    @PostMapping("/auth/board/reply")
    public String registerReply(@RequestBody Reply reply) {
        Reply createReply = replyService.createReply(reply.getUser(), reply.getBoard(), reply.getContent());

        return "댓글이 저장되었습니다.";
    }
}
