package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.dto.Result;
import com.blogproject.myblog.dto.reply.ReplyByBoard;
import com.blogproject.myblog.dto.reply.ReplyDto;
import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.entity.Reply;
import com.blogproject.myblog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    // 특정 회원의 댓글 조회
    @GetMapping("/api/board/reply/{userId}")
    public Result findAllReplyByUser(@PathVariable("userId") Long id){
        List<Reply> findReplys = replyService.findReplyByUser(id);

        List<ReplyDto> result = findReplys.stream()
                .map(r -> new ReplyDto(r.getUser().getId(), r.getBoard().getId(), r.getContent(), r.getCreatedTime()))
                .collect(Collectors.toList());
        return new Result(result, result.size());
    }

    // 특정 게시판의 모든 댓글 조회
    @GetMapping("/api/board/{boardId}/reply")
    public Result findAllReplyByBoard(@PathVariable("boardId") Long id){
        List<Reply> findReplys = replyService.findReplyByBoard(id);

        List<ReplyByBoard> result = findReplys.stream()
                .map(r -> new ReplyByBoard(r.getBoard().getId(), r.getBoard().getTitle(),r.getBoard().getContent(),r.getId(),r.getContent()))
                .collect(Collectors.toList());
        return new Result(result, result.size());
    }
}
