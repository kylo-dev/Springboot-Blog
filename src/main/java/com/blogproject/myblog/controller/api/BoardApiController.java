package com.blogproject.myblog.controller.api;

import com.blogproject.myblog.config.auth.PrincipalDetail;
import com.blogproject.myblog.dto.board.BoardDto;
import com.blogproject.myblog.dto.reply.ReplySaveRequestDto;
import com.blogproject.myblog.dto.ResponseDto;
import com.blogproject.myblog.dto.Result;
import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.service.BoardService;
import com.blogproject.myblog.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final ReplyService replyService;

    // 모든 게시판 조회 API
    @GetMapping("/api/boards")
    public Result findBoards(){
        List<Board> findBoards = boardService.findAllBoardWithUser();

        List<BoardDto> result = findBoards.stream()
                .map(b -> new BoardDto(b.getTitle(), b.getContent(), b.getCount(), b.getUser().getId()))
                .collect(Collectors.toList());
        return new Result(result, result.size());
    }

    // 특정 게시판 조회 API
    @GetMapping("/api/board/{id}")
    public BoardDto findBoard(@PathVariable("id") Long id) {
        Board findBoard = boardService.findById(id);
        return new BoardDto(findBoard.getTitle(), findBoard.getContent(),findBoard.getCount(), findBoard.getUser().getId());
    }

    // 게시판 생성 API 1
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody BoardDto boardDto, @AuthenticationPrincipal PrincipalDetail principal) {
        Board board = Board.builder()
                        .title(boardDto.getTitle())
                        .content(boardDto.getContent())
                        .build();
        boardService.write(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/v2")
    public CreateResponse saveV2(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        Long id = boardService.write(board, principal.getUser());
        Board findBoard = boardService.findById(id);
        return new CreateResponse(findBoard.getUser().getId());
    }

    // 게시판 삭제 API 1
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        try {
            boardService.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return new ResponseDto<Integer>(HttpStatus.BAD_REQUEST.value(), 0);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 게시판 삭제 API 2
    @DeleteMapping("/api/board/v2/{id}")
    public CreateResponse deleteV2(@PathVariable Long id) {
        boardService.deleteById(id);
        return new CreateResponse(id);
    }

    // 게시판 수정 API 1
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        boardService.update(id, boardDto.getTitle(), boardDto.getContent());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @Data
    @AllArgsConstructor
    static class CreateResponse {
        private Long boardId;
    }

    // 게시판에 댓글 작성
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySaveDto(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
        replyService.writeReply(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 게시판에 작성된 댓글 삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable Long boardId, @PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //    @PostMapping("/api/board/{boardId}/reply")
//    public ResponseDto<Integer> replySave(@PathVariable Long boardId,
//                                          @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
//        System.out.println("writeReply() 실행 전");
//        boardService.writeReply(principal.getUser(), boardId, reply);
//        System.out.println("writeReply() 실행 후");
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }
}
