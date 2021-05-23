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
public class BoardEditTest {
    @Autowired
    private BoardService boardService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void editBoardTitleTestForSuccess() throws Exception {
        // bookmark id 1 required in db
        Board board = boardService.findBoardById(1);
        // title change test
        Board beforeBoard = boardService.findBoardById(1);
        logger.info("before: " + beforeBoard.toString());
        board.setTitle("[test] hello world " + new Date().toString());
        Board result = boardService.editBoard(board.getBoardSeq(), board);
        logger.info("after: " + result.toString());
        Assertions.assertNotEquals(beforeBoard.getTitle(), result.getTitle());
        Assertions.assertEquals(beforeBoard.getContent(), result.getContent());
        Assertions.assertNotEquals(beforeBoard.getUpdateDate(), result.getUpdateDate());
    }

    @Test
    public void editBoardContentTestForSuccess() throws Exception {
        // bookmark id 1 required in db
        Board board = boardService.findBoardById(1);
        // content change test
        Board beforeBoard = boardService.findBoardById(1);;
        logger.info("before: " + beforeBoard.toString());
        board.setContent("test content! " + new Random().nextInt());
        Board result = boardService.editBoard(board.getBoardSeq(), board);
        logger.info("after: " + result.toString());
        Assertions.assertEquals(beforeBoard.getTitle(), result.getTitle());
        Assertions.assertNotEquals(beforeBoard.getContent(), result.getContent());
        Assertions.assertNotEquals(beforeBoard.getUpdateDate(), result.getUpdateDate());
    }
}
