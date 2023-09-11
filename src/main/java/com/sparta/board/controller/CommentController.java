package com.sparta.board.controller;


import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, userDetails.getUser());//userDetails.getUser() : 로그인한 사용자 정보
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id,userDetails.getUser());//userDetails.getUser() : 로그인한 사용자 정보
    }

    @PutMapping("/comments/{id}/like")
    public ResponseEntity<String> likesComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.likesComment(id, userDetails.getUser());
    }









}
