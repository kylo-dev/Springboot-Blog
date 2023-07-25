package com.blogproject.myblog.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인

    @ColumnDefault("0")
    private int count; // 조회수

    @ManyToOne(fetch = FetchType.LAZY) // Many = Board, One = User
    @JoinColumn(name = "user_id")
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 가능

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Reply> replies = new ArrayList<>();

}