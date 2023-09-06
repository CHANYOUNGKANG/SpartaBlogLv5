package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.repository.BoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;//

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        //RequestDto -> Entity
        Board board = new Board(requestDto, user);//BoardRequestDto를 Board로 바꿔줌

        // DB 저장
        Board saveBoard = boardRepository.save(board);

        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);

        return boardResponseDto;
    }

    public List<BoardResponseDto> getBoards() {
        // DB 조회
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public List<BoardResponseDto> getBoardsByKeyword(String keyword) {
        return boardRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream().map(BoardResponseDto::new).toList();
    }

    @Transactional //변경감지
    public ResponseEntity<String> updateBoard(Long id, BoardRequestDto requestDto, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id); // 이친구한테 뺏어온다.
        System.out.println("board.getUser=" + board.getUser());//
        System.out.println("board.getUser.getUserName() =" + board.getUser().getUsername());
        System.out.println("user=" + user);
        System.out.println("user.getUserName() =" + user.getUsername());
        //디비와 토큰 유저 네임 비교

        if (board.getUser().getUsername().equals(user.getUsername())) {
            board.update(requestDto, user);
            return ResponseEntity.ok().body("수정 완료");
        } else {
            return ResponseEntity.badRequest().body("유저가 일치하지 않습니다.");
        }
        // 해당 메모가 로그인한 사용자의 메모인지 확인
        //board 내용 수정
    }

    public ResponseEntity<String> deleteBoard(Long id, User user) {
        //  해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id); // 이친구한테 뺏어온다.
        if (board.getUser().getUsername().equals(user.getUsername())) {//디비와 토큰 유저 네임 비교
            boardRepository.delete(board);
            return ResponseEntity.ok().body("삭제 완료");
        } else {
            return ResponseEntity.badRequest().body("유저가 일치하지 않습니다.");
        }
    }
    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
    }
}
