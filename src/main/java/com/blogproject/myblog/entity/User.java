package com.blogproject.myblog.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username; // 아이디

    @Column(nullable = false) // 123456 => 해쉬 (비밀번호 암호화)
    private String password; // 패스워드

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role; // Enum을 쓰는게 좋음. - admin, user, manager

    @Column
    private String oauth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();
}
