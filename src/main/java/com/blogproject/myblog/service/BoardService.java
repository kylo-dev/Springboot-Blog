package com.blogproject.myblog.service;

import com.blogproject.myblog.entity.Board;
import com.blogproject.myblog.entity.User;
import com.blogproject.myblog.repository.BoardRepository;
import com.blogproject.myblog.repository.ReplyRepository;
import com.blogproject.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    // User 정보 매핑
    @Transactional
    public Long write(Board board, User user) {
        board.setCount(0); // 조회수 0으로 설정
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 작성자 id를 찾을 수 없습니다.");
                });
        board.setUser(findUser);
        // user.getBoards().add(board);
        Board save = boardRepository.save(board);
        return save.getId();
    }

    public Page<Board> findAll(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        // List<Board> boards = boardPage.getContent();
        return boardPage;
    }

    public List<Board> findAllBoardWithUser() {
        return boardRepository.findAllBoardWithUser();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 게시글 id를 찾을 수 없습니다.");
                });
    }

    public Board findBoardWithUser(Long id) {
        return boardRepository.findBoardWithUser(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 게시글 id를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    @Modifying(clearAutomatically = true)
    public void update(Long id, String title, String content) {
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 : 게시판을 찾을 수 없습니다.");
        }); // 영속화 완료
        board.setTitle(title);
        board.setContent(content); // 해당 함수 종료시에 트랜잭션이 종료 -> Dirty Checking 발생!
    }

//    @Transactional
//    public void update(Long id, Board requestBoard) {
//         Board board = boardRepository.findById(id).orElseThrow(()->{
//                        return new IllegalArgumentException("글 찾기 실패 : 게시판을 찾을 수 없습니다.");
//                    }); // 영속화 완료
//         board.setTitle(requestBoard.getTitle());
//         board.setContent(requestBoard.getContent()); // 해당 함수 종료시에 트랜잭션이 종료 -> Dirty Checking 발생!
//    }

//    @Transactional
//    public void writeReply(User user, Long boardId, Reply reply) {
//        /**
//         * 작성된 댓글 User, Board와 연동하기
//         * 연동이후 Repository에 저장
//         */
//        System.out.println("Service 클래스 writeReply() 실행");
//        User registerUser = userRepository.findById(user.getId()).orElseThrow(()->{
//            return new IllegalArgumentException("아이디 찾기 실패 : 아이디를 찾을 수 없습니다.");
//        });
//        for (Reply userReply: registerUser.getReplies()) {
//            System.out.println("set 하기 직전 유저의 댓글 확인 : " + userReply.getContent());
//        }
//        for (Reply userReply: findById(boardId).getReplies()) {
//            System.out.println("set 유저의 댓글 확인 : " + userReply.getContent());
//        }
//        reply.setUser(registerUser);
//        reply.setBoard(findById(boardId));
//        replyRepository.save(reply);
//        System.out.println("replyRepository에 저장");
//        System.out.println("해당 유저의 댓글 개수 : " + registerUser.getReplies().size());
//        for (Reply userReply: registerUser.getReplies()) {
//            System.out.println("유저의 댓글 확인 : " + userReply.getContent());
//        }
//        System.out.println("해당 댓글의 댓글 개수 : " + findById(boardId).getReplies().size());
//        for (Reply userReply: findById(boardId).getReplies()) {
//            System.out.println("유저의 댓글 확인 : " + userReply.getContent());
//        }
//    }

}
