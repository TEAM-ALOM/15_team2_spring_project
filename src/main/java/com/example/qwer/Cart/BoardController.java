package com.example.qwer;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public Board postBoard(@RequestBody Board board){
        return boardService.createBoard(board);
    }

    @PutMapping("/{boardId}")
    public Board updateBoard(@PathVariable Long boardId, @RequestBody Board board){
        Board currentBoard = boardService.updateBoard(board, boardId);
        System.out.println("Post 메소드 실행"+currentBoard);
        return currentBoard;
    }

    @GetMapping("/{boardId}")
    public List<Board> getBoard(@PathVariable Long boardId){
        System.out.println("Post 메소드 실행");
        return boardService.getAllBoards(boardId);
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId){
        System.out.println("Post 메소드 실행");
        boardService.deleteBoard(boardId);
        return "Board 삭제 메소드";
    }

}
