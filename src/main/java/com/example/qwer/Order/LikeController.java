package com.example.qwer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;
    private final BoardService boardService;

    public LikeController(LikeService likeService, BoardService boardService) {
        this.likeService = likeService;
        this.boardService = boardService;
    }
    @PostMapping("/boards/{boardId}/likes")
    public Likes postLike(@PathVariable Long boardId, @RequestBody Likes like){
        Likes curLike=likeService.createLike(like.getUser_id(),boardId);
        return curLike;
    }

    @DeleteMapping("/boards/{boardId}/likes")
    public String deleteLike(@PathVariable Long boardId){
        likeService.deleteLikesByBoardId(boardId);
        return "보드 좋아요 삭제 완료.";
    }

    @GetMapping("/boards/{boardId}/likes")
    public List<Likes> getLikes(@PathVariable Long boardId){
        return likeService.getBoardsLike(boardId);
    }

    @GetMapping("/users/{userId}/likes")
    public List<Likes> getUserLikes(@PathVariable Long userId){
        return likeService.getUserLike(userId);
    }


}
