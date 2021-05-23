package com.board.springbd.repository;

import com.board.springbd.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardSeqAndState(Long boardSeq, boolean state);
    List<Board> findByState(boolean state);
    List<Board> findByTitleContainsAndState(String title, boolean state);
    List<Board> findByContentContainsAndState(String content, boolean state);
    List<Board> findByTitleContainsOrContentContainsAndState(String title, String content, boolean state);
}
