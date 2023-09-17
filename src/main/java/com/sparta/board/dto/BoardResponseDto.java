package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class  BoardResponseDto  {
    private Long id;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private Integer likeCount;
    private List<CommentResponseDto> commentList = new ArrayList<>();//commentResponseDto를 담을 수 있는 리스트를 만듬//

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.contents = board.getContents();
        this.username = board.getUser().getUsername();//board에 있는 user의 username을 가져옴 //board.getUser().getUsername();를 실행시킴 //board.getUser()는 board에 있는 user를 가져옴 //board.getUser().getUsername();는 board에 있는 user의 username을 가져옴
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.title = board.getTitle();
        for (Comment comment : board.getCommentsList()) {
            commentList.add(new CommentResponseDto(comment));//commentList에 commentResponseDto를 넣어줌 //new CommentResponseDto(comment)를 실행시킴 //new CommentResponseDto는 밑에 만들어줌
        }
        commentList.sort((a,b)->b.getCreatedAt().compareTo(a.getCreatedAt()));//정렬 //commentList.sort((a,b)->b.getCreatedAt().compareTo(a.getCreatedAt()));를 실행시킴 //commentList.sort((a,b)->b.getCreatedAt().compareTo(a.getCreatedAt()));는 commentList를 정렬시킴
        this.likeCount = board.getLikeList().size();//likeCount에 board.getLikeList().size();를 넣어줌 //board.getLikeList().size();를 실행시킴 //board.getLikeList()는 board에 있는 likeList를 가져옴 //board.getLikeList().size();는 board에 있는 likeList의 크기를 가져옴


    }
//왔다 갔다할 데이터형태를 직접만듬 //이건 반환할때
}
