package com.blogproject.myblog.repository;

import com.blogproject.myblog.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
