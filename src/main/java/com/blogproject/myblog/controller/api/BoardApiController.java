package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.entity.Reply;
import com.blogproject.myblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.write(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        boardService.deleteById(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board) {
        boardService.update(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@PathVariable Long boardId,
                                          @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
        System.out.println("writeReply() 실행 전");
        boardService.writeReply(principal.getUser(), boardId, reply);
        System.out.println("writeReply() 실행 후");
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
