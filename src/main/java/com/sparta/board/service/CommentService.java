package com.sparta.board.service;



import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor//final이 붙은 필드를 인자값으로 하는 생성자를 만들어줌
@Service//서비스라고 스프링에 알려줌
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;




    //댓글을 달려면 게시글을 찾아야함
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        //게시글 찾기
        Board board = findBoard(requestDto.getBoardId());//게시글의 id를 찾아서 board에 넣어줌 //findBoard는 밑에 만들어줌 //boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 글은 존재하지 않습니다."));를 실행시킴
        //RequestDto -> Entity
        // DB 저장
        Comment comment = commentRepository.save(new Comment(requestDto, board, user));  // Entity -> ResponseDto //commentRepository.save(new Comment(requestDto, board, user));를 실행시킴 //commentRepository.save(new Comment(requestDto, board, user));는 CommentRepository에 있는 save를 실행시킴
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);//commentResponseDto에 comment를 넣어줌 //CommentResponseDto(comment)를 실행시킴 //CommentResponseDto는 밑에 만들어줌 //
        return commentResponseDto;//commentResponseDto를 리턴
    }

    @Transactional //변경감지
    public ResponseEntity<String> updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
        if (user.getRole().equals(UserRoleEnum.ADMIN)){//관리자인지 확인
            comment.update(requestDto, user);
            return ResponseEntity.ok().body("수정 완료");
        }
        if (comment.getUser().getUsername().equals(user.getUsername())){//디비와 토큰 유저 네임 비교
            comment.update(requestDto, user);
            return ResponseEntity.ok().body("수정 완료");
        } else {
            return ResponseEntity.badRequest().body("유저가 일치하지 않습니다.");
        }
    }

    public ResponseEntity<String> deleteComment(Long id, User user) {
        //  해당 메모가 DB에 존재하는지 확인
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
        if (user.getRole().equals(UserRoleEnum.ADMIN)){//관리자인지 확인
            commentRepository.deleteById(id);
            return ResponseEntity.ok().body("삭제 완료");
        }


        if (comment.getUser().getUsername().equals(user.getUsername())){//디비와 토큰 유저 네임 비교
            commentRepository.deleteById(id);
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