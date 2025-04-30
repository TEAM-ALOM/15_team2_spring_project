package com.example.qwer;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    @Query("SELECT l FROM Likes l WHERE l.user_id = :userId")
    List<Likes> findAllByUserId(Long id);
    @Query("SELECT l FROM Likes l WHERE l.board_id = :boardId")
    List<Likes> findAllByBoardId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Likes l WHERE l.board_id = :boardId")
    void deleteByBoard_id(Long boardId);
}
