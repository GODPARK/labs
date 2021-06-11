package com.board.springbd.service;

import com.board.springbd.exception.BoardException;
import com.board.springbd.model.Board;
import com.board.springbd.model.Category;
import com.board.springbd.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CategoryService categoryService;

    public Board findBoardById(long seq) {
        return boardRepository.findByBoardSeqAndState(seq, true);
    }

    public List<Board> findAllBoard() {
        return boardRepository.findByState(true);
    }

    public List<Board> findBoardByTitle(String title) {
        return boardRepository.findByTitleContainsAndState(title, true);
    }

    public List<Board> findBoardByContent(String content) {
        return boardRepository.findByContentContainsAndState(content, true);
    }

    public List<Board> findBoardByTitleAndContent(String title, String content) {
        return boardRepository.findByTitleContainsOrContentContainsAndState(title, content, true);
    }

    public Board addGoodCount(long seq) {
        Board goodBoard = boardRepository.findByBoardSeqAndState(seq, true);
        goodBoard.setGoodCount(goodBoard.getGoodCount() + 1);
        return boardRepository.save(goodBoard);
    }

    public Board saveBoard(Board board) {
        Category category = categoryService.searchCategoryById(board.getCategoryId());
        if (board.getTitle().isBlank()) throw new BoardException("title is empty");
        Board saveBoard = Board.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .categoryId(category.getCategoryId())
                .goodCount(0)
                .createDate(new Date())
                .state(true)
                .build();
        return boardRepository.save(saveBoard);
    }

    public Board editBoard(long boardId, Board board) {
        Board editBoard = boardRepository.findByBoardSeqAndState(boardId, true);
        if (editBoard == null) throw new BoardException("edit board is empty");
        editBoard.setTitle(board.getTitle());
        editBoard.setContent(board.getContent());
        editBoard.setUpdateDate(new Date());
        return boardRepository.save(editBoard);
    }

    public Board deleteBoard(long boardId) {
        Board deleteBoard = boardRepository.findByBoardSeqAndState(boardId, true);
        deleteBoard.setState(false);
        deleteBoard.setDeleteDate(new Date());
        return boardRepository.save(deleteBoard);
    }
}
