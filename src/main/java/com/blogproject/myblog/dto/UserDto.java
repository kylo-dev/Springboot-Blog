package com.blogproject.myblog.dto;

import com.blogproject.myblog.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String username;
    private String email;
    private UserRole role;
}
