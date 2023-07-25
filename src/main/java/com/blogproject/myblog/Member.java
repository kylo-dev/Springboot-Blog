package com.blogproject.myblog;

import lombok.*;

//@Getter
//@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private int id;
    private String userName;
    private String password;
    private String email;

}
