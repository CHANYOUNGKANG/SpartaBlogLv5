package com.sparta.board.repository;

import com.sparta.board.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByBoardIdAndUserId(Long boardId, Long userId);

    Optional<Likes> findByCommentIdAndUserId(Long id, Long id1);
}
