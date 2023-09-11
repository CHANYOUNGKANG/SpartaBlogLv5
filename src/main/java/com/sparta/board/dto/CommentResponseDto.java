package com.sparta.board.dto;


import com.sparta.board.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;
    private Integer likeCount;

    public CommentResponseDto(Comment comment) {//commentResponseDto에 comment를 넣어줌
        this.id = comment.getId();//댓글의 id
        this.username = comment.getUser().getUsername();//댓글을 쓴사람의 이름
        this.comment = comment.getComments();//댓글 내용
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likeCount = comment.getLikeList().size();//댓글의 좋아요 수
    }
}