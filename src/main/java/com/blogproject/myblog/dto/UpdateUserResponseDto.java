package com.blogproject.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponseDto {

    private Long id;
    private String username;
    private String email;
}
