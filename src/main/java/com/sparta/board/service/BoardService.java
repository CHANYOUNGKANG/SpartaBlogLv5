package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.repository.BoardRepository;
import org.springframework.http.HttpStatus;
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
        Board saveBoard = boardRepository.save(board);//boardRepository.save(board);를 실행시킴 //boardRepository.save(board);는 BoardRepository에 있는 save를 실행시킴//boardRepository.save(board);는 board를 저장시킴 //boardRepository.save(board);는 board를 저장시킨 후 saveBoard에 넣어줌
        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);//boardResponseDto에 saveBoard를 넣어줌 //BoardResponseDto(saveBoard)를 실행시킴 //BoardResponseDto는 밑에 만들어줌

        return boardResponseDto;//boardResponseDto를 리턴
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
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {//관리자인지 확인 //board.update(requestDto, user);를 실행시킴 //board.update는 Board에 있는 update를 실행시킴 //board.update(requestDto, user);는 BoardRequestDto를 Board로 바꿔줌
            board.update(requestDto, user);
            return ResponseEntity.status(200).body("상태코드 : " + HttpStatus.OK.value() + " 메세지 : 관리자 권한 게시물 수정 성공"); }
        //디비와 토큰 유저 네임 비교
        if (board.getUser().getUsername().equals(user.getUsername())) {
            board.update(requestDto, user);//board 내용 수정 //board.update(requestDto, user);를 실행시킴 //board.update는 Board에 있는 update를 실행시킴 //board.update(requestDto, user);는 BoardRequestDto를 Board로 바꿔줌
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
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            boardRepository.delete(board);
            return ResponseEntity.ok().body("삭제 완료");
        }
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
