package com.blogproject.myblog.repository;

import com.blogproject.myblog.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 상세보기 시 Board - User 지연로딩 최적화
    @Query("select b from Board b join fetch b.user where b.id =:id")
    Optional<Board> findBoardWithUser(@Param("id") Long id);

    // 모든 게시판 조회 시 회원 정보도 같이 불러오기
    @Query("select b from Board b join fetch b.user")
    List<Board> findAllBoardWithUser();
}
