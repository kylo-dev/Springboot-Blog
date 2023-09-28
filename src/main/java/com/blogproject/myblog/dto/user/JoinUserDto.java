package com.blogproject.myblog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinUserDto {

    private String username;
    private String password;
    private String email;
}
