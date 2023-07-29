package com.blogproject.myblog.service;

import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    // User 정보 매핑
    @Transactional
    public void write(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> findAll(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        // List<Board> boards = boardPage.getContent();

        return boardPage;
    }

    public Board findById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Board requestBoard) {
         Board board = boardRepository.findById(id).orElseThrow(()->{
                        return new IllegalArgumentException("글 찾기를 실패 : 아이디를 찾을 수 없습니다.");
                    }); // 영속화 완료
         board.setTitle(requestBoard.getTitle());
         board.setContent(requestBoard.getContent()); // 해당 함수 종료시에 트랜잭션이 종료 -> Dirty Checking 발생!
    }
}
