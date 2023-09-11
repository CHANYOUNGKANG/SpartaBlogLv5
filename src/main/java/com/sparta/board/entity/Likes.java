package com.sparta.board.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "likes") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)//fetch = FetchType.LAZY는 필요할 때만 가져온다는 뜻
    @JoinColumn(name = "board_id", nullable = true)//외래키 //board_id _id는 알아서 _id를 붙여줌 //nullable = false는 반드시 있어야한다는 뜻 //board_id는 board테이블의 id를 참조한다는 뜻
    private Board board;//board_id는 board테이블의 id를 참조한다는 뜻


    @ManyToOne(fetch = FetchType.LAZY) //fetch = FetchType.LAZY는 필요할 때만 가져온다는 뜻
    @JoinColumn(name = "user_id", nullable = false) //외래키 //user_id _id는 알아서 _id를 붙여줌 //nullable = false는 반드시 있어야한다는 뜻 //user_id는 user테이블의 id를 참조한다는 뜻
    private User user;//user_id는 user테이블의 id를 참조한다는 뜻

    @ManyToOne(fetch = FetchType.LAZY)//fetch = FetchType.LAZY는 필요할 때만 가져온다는 뜻
    @JoinColumn(name = "comment_id", nullable = true)//외래키 //comment_id _id는 알아서 _id를 붙여줌 //nullable = false는 반드시 있어야한다는 뜻 //comment_id는 comment테이블의 id를 참조한다는 뜻
    private Comment comment; //comment_id는 comment테이블의 id를 참조한다는 뜻


    public Likes(Board board, User user, Comment comment) {//생성자
        this.board = board;
        this.user = user;
        this.comment = comment;
    }
    public Likes(Board board, User user) {//생성자
        this.board = board;
        this.user = user;
    }
    public Likes(Comment comment, User user) {//생성자
        this.comment = comment;
        this.user = user;
    }

}
