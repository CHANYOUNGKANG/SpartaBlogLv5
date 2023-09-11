package com.sparta.board.entity;


import com.sparta.board.dto.BoardRequestDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "board") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentsList = new ArrayList<>();//board에 있는 commentList를 가져옴 //mappedBy는 연관관계의 주인이 아니다(난 FK가 아니에요) 라는 뜻 //board는 comment를 가지고 있지만 comment는 board를 가지고 있지 않다는 뜻 //board는 comment를 참조할 수 있지만 comment는 board를 참조할 수 없다는 뜻

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Likes> likeList = new ArrayList<>();//board에 있는 likeList를 가져옴 //mappedBy는 연관관계의 주인이 아니다(난 FK가 아니에요) 라는 뜻 //board는 like를 가지고 있지만 like는 board를 가지고 있지 않다는 뜻 //board는 like를 참조할 수 있지만 like는 board를 참조할 수 없다는 뜻



    public Board(BoardRequestDto requestDto, User user) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto, User user) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.user = user;
    }



}
