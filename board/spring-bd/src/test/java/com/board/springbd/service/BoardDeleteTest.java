package com.board.springbd.service;

import com.board.springbd.model.Board;
import com.board.springbd.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardDeleteTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public  void deleteBoardTestForSuccess() throws Exception {
        Board board = boardService.findBoardById(1);
        Board result = boardService.deleteBoard(1);
        Assertions.assertNotEquals(board.isState(), result.isState());
        logger.info("before: " + board.toString());
        logger.info("after: " + result.toString());

        Board check = boardService.findBoardById(1);
        logger.info("check: " + check);
        Assertions.assertNull(check);

        result.setState(true);
        result.setDeleteDate(null);
        Board rollback = boardRepository.save(result);
        logger.info("rollback: " + rollback.toString());
    }
}
