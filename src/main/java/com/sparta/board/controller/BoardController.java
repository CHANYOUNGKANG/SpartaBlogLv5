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

    @PostMapping("/boards")//생성
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());//userDetails.getUser() : 로그인한 사용자 정보
    }
    @GetMapping("/boards/{id}")//상세보기
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }


    @GetMapping("/boards")//전체보기
    public List<BoardResponseDto> getBoards() {//전체보기

        return boardService.getBoards();//전체보기
    }

    @GetMapping("/boards/contents")//전체보기
    public List<BoardResponseDto> getBoardsByKeyword(String keyword){//keyword : 검색어
        return boardService.getBoardsByKeyword(keyword);//검색어를 넘겨줌
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }
    @DeleteMapping("/boards/{id}")//삭제
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id,userDetails.getUser());//userDetails.getUser() : 로그인한 사용자 정보
    }

    @PutMapping("/boards/{id}/like")
    public ResponseEntity<String> likeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.likeBoard(id, userDetails.getUser());
    }




}
