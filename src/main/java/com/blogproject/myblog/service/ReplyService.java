package com.blogproject.myblog.service;

import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.entity.Reply;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.repository.BoardRepository;
import com.blogproject.myblog.repository.ReplyRepository;
import com.blogproject.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Reply createReply(User user, Board board, String content) {

        // 유저 조회
        User findUser = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패: 아이디를 찾을 수 없습니다.");
        });

        // 글 조회
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 : 해당 글을 찾을 수 없습니다.");
        });

        // 댓글 생성
        Reply reply = Reply.createReply(findUser, findBoard, content);
        replyRepository.save(reply);
        return reply;
    }

}
