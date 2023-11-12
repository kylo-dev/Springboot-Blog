package com.blogproject.myblog.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

    private Long user_id;
    private Long board_id;
    private String content;
    private LocalDateTime created_time;
}
