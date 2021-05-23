package com.board.springbd.service;

import com.board.springbd.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardSearchTest {
    @Autowired
    private BoardService boardService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void searchBoardTestByIdForSuccess() throws Exception {
        Board board = boardService.findBoardById(1);
        Assertions.assertNotNull(board);
        logger.info(board.toString());
    }

    @Test
    public void searchBoardTestAllForSuccess() throws Exception {
        List<Board> boardList = boardService.findAllBoard();
        Assertions.assertFalse(boardList.isEmpty());
        logger.info("total board: " + boardList.size());
    }

    @Test void searchBoardTestByTitleForSuccess() throws Exception {
        String findStr = "hello world";
        List<Board> boardList = boardService.findBoardByTitle(findStr);
        Assertions.assertFalse(boardList.isEmpty());
        logger.info("find title board (" + findStr + ") : " + boardList.size());
        logger.info(boardList.toString());

        String noneFindStr = "what the";
        List<Board> noneBoardList = boardService.findBoardByTitle(noneFindStr);
        Assertions.assertTrue(noneBoardList.isEmpty());
        logger.info("find none exist title board (" + noneFindStr + ") : " + noneBoardList.size());
        logger.info(noneBoardList.toString());
    }

    @Test void searchBoardTestByContentForSuccess() throws Exception {
        String findStr = "test content";
        List<Board> boardList = boardService.findBoardByContent(findStr);
        Assertions.assertFalse(boardList.isEmpty());
        logger.info("find content board (" + findStr + ") : " + boardList.size());
        logger.info(boardList.toString());

        String noneFindStr = "what the";
        List<Board> noneBoardList = boardService.findBoardByContent(noneFindStr);
        Assertions.assertTrue(noneBoardList.isEmpty());
        logger.info("find none exist content board (" + noneFindStr + ") : " + noneBoardList.size());
        logger.info(noneBoardList.toString());
    }

    @Test void searchBoardTestByTitleAndContentForSuccess() throws Exception {
        String findStr = "test";
        List<Board> boardList = boardService.findBoardByTitleAndContent(findStr, findStr);
        Assertions.assertFalse(boardList.isEmpty());
        logger.info("find title,content board (" + findStr + ") : " + boardList.size());
        logger.info(boardList.toString());

        String onlyTitleFindStr = "hello world";
        List<Board> onlyTitleBoardList = boardService.findBoardByTitleAndContent(onlyTitleFindStr, onlyTitleFindStr);
        Assertions.assertFalse(onlyTitleBoardList.isEmpty());
        logger.info("find only title board (" + onlyTitleFindStr + ") : " + onlyTitleBoardList.size());
        logger.info(onlyTitleBoardList.toString());

        String onlyContentFindStr = "content";
        List<Board> onlyContentBoardList = boardService.findBoardByTitleAndContent(onlyContentFindStr, onlyContentFindStr);
        Assertions.assertFalse(onlyContentBoardList.isEmpty());
        logger.info("find only content board (" + onlyContentFindStr + ") : " + onlyContentBoardList.size());
        logger.info(onlyContentBoardList.toString());
    }
}
