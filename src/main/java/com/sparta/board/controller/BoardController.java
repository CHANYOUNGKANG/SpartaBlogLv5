package com.sparta.board.controller;


import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());//userDetails.getUser() : 로그인한 사용자 정보
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards() {

        return boardService.getBoards();
    }

    @GetMapping("/boards/contents")
    public List<BoardResponseDto> getBoardsByKeyword(String keyword){
        return boardService.getBoardsByKeyword(keyword);
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id,userDetails.getUser());//userDetails.getUser() : 로그인한 사용자 정보
    }
}
