package com.blogproject.myblog.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyByBoard {

    private Long board_id;
    private String title;
    private String board_content;
    private Long reply_id;
    private String reply_content;
}
