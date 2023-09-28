package com.blogproject.myblog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

    private Long id;

    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String email;
}
