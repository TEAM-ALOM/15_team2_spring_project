package com.example.qwer;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Likes createLike(Long userId, Long boardId){
        Likes curLike = new Likes();
        curLike.setBoard_id(boardId);
        curLike.setUser_id(userId);
        curLike.setLikedAt(LocalDateTime.now());
        return likeRepository.save(curLike);
    }


    @Transactional
    public void deleteLikesByBoardId(Long boardId) {
        likeRepository.deleteByBoard_id(boardId);
    }

    public List<Likes> getUserLike(Long userId){
        return likeRepository.findAllByUserId(userId);
    }

    public List<Likes> getBoardsLike(Long boardId){
        return likeRepository.findAllByBoardId(boardId);
    }




}
