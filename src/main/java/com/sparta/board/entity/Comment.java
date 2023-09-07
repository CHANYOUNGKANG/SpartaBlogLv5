package com.sparta.board.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
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
