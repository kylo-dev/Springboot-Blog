package com.blogproject.myblog.repository;

import com.blogproject.myblog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.user.id = :id")
    List<Reply> findReplyByUser(@Param("id") Long id);

    @Query("select r from Reply r join fetch r.board where r.board.id = :id")
    List<Reply> findReplyByBoard(@Param("id") Long id);
}
