package com.example.qwer;

import com.example.qwer.Board;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board createBoard(Board board){
        Board newBoard = new Board();
        newBoard.setTitle(board.getTitle());
        newBoard.setContent(board.getContent());
        newBoard.setUser_id(board.getUser_id());
        newBoard.setCreated_at(LocalDateTime.now());
        return boardRepository.save(newBoard);
    }

    public Board updateBoard(Board board, Long id){
        Board curBoard = boardRepository.findById(id).orElse(null);
        Long curBoardId=board.getId();
        if(!curBoardId.equals(id)){
            throw new RuntimeException("board id가 일치하지 않습니다.");
        }
        curBoard.setTitle(board.getTitle());
        curBoard.setId(board.getId());
        curBoard.setUser_id(board.getUser_id());
        curBoard.setContent(board.getContent());
        return curBoard;
    }

    public String deleteBoard(Long id){
        Board curBoard = boardRepository.findById(id).orElse(null);
        boardRepository.delete(curBoard);
        return "board deleted";
    }

    public Board getBoard(Long id){
        Board curBoard = boardRepository.findById(id).orElse(null);
        return curBoard;
    }

    public List<Board> getAllBoards(Long id){
        return getAllBoards(id);
    }




}
