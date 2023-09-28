package com.blogproject.myblog.repository;

import com.blogproject.myblog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 네이티브 쿼리로 댓글 작성하기
//    @Modifying
//    @Query(value = "INSERT INTO reply(user_id, board_id, content, created_time) VALUES(?1,?2,?3, now())", nativeQuery = true)
//    int mSave(Long userId, Long boardId, String content); // 업데이트된 행의 개수를 리턴해줌
}
