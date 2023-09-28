package com.blogproject.myblog.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDto {

    private String title;
    private String content;
    private int count;
}
