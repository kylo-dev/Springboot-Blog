package com.blogproject.myblog.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private String title;
    private String content;
    private int count;
    private Long user_id;
}