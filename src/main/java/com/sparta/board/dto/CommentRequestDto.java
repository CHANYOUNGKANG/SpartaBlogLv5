package com.sparta.board.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long boardId;
    private String comment;
}
