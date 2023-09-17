package com.sparta.board.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter//getter는 사용하지 않는 것이 좋음
@Setter//setter는 사용하지 않는 것이 좋음
@Table(name = "comment") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "comments", nullable = false, length = 500)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)//외래키 //board_id _id는 알아서 _id를 붙여줌 //nullable = false는 반드시 있어야한다는 뜻 //board_id는 board테이블의 id를 참조한다는 뜻
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Likes> likeList= new ArrayList<>();//comment에 있는 likeList를 가져옴 //mappedBy는 연관관계의 주인이 아니다(난 FK가 아니에요) 라는 뜻 //comment는 like를 가지고 있지만 like는 comment를 가지고 있지 않다는 뜻 //comment는 like를 참조할 수 있지만 like는 comment를 참조할 수 없다는 뜻

    public Comment(CommentRequestDto requestDto, Board board, User user) {
        this.comments = requestDto.getComment();
        this.board = board;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto, User user) {
        this.comments = requestDto.getComment();
        this.user = user;
    }
}
