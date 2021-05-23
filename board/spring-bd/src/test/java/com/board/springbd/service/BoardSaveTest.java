package com.board.springbd.service;

import com.board.springbd.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Random;

@SpringBootTest
public class BoardSaveTest {
    @Autowired
    private BoardService boardService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void newBoardTestForSuccess() throws Exception {
        Board board = Board.builder()
                .title("[test] hello world " + new Date().toString())
                .content("test content! " + new Random().nextInt()).build();

        Board resultBoard = boardService.saveBoard(board);
        logger.info("Result Board: " + resultBoard.toString());
        Assertions.assertNotNull(resultBoard);
    }
}
