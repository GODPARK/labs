package com.board.springbd.controller;

import com.board.springbd.exception.BoardException;
import com.board.springbd.model.Board;
import com.board.springbd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<Board>> getAllBoard() {
        return ResponseEntity.ok().body(boardService.findAllBoard());
    }

    @GetMapping(path = "/{boardId}", produces = "application/json")
    public ResponseEntity<Board> getBoardById(@PathVariable(value = "boardId", required = true) long boardId) {
        return ResponseEntity.ok().body(boardService.findBoardById(boardId));
    }

    @GetMapping(path = "/search", produces = "application/json")
    public ResponseEntity<List<Board>> searchBoardByTitle(
            @RequestParam(value = "title", required = false) String title, @RequestParam(value = "content", required = false) String content) {
        if (title != null && content == null) return ResponseEntity.ok().body(boardService.findBoardByTitle(title));
        else if (title == null && content != null) return ResponseEntity.ok().body(boardService.findBoardByContent(content));
        else if (title != null && content != null) return ResponseEntity.ok().body(boardService.findBoardByTitleAndContent(title, content));
        else return ResponseEntity.ok().body(boardService.findAllBoard());
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Board> saveNewBoard(@RequestBody Board board) {
        if (board == null) throw new BoardException("body is empty");
        return ResponseEntity.ok().body(boardService.saveBoard(board));
    }

    @PatchMapping(path = "/{boardId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Board> editBorad (
            @PathVariable(value = "boardId", required = true) long boardId, @RequestBody Board board) {
        if (board == null) throw new BoardException("body is empty");
        return ResponseEntity.ok().body(boardService.editBoard(boardId, board));
    }

    @PatchMapping(path = "/{boardId}/category/{categoryId}", produces = "application/json")
    public ResponseEntity<Board> editBoardCategory (
            @PathVariable(value = "boardId") long boardId,
            @PathVariable(value = "categoryId") long categoryId
    ) {
        return ResponseEntity.ok().body(boardService.editBoardCategory(boardId, categoryId));
    }

    @PatchMapping(path = "/{boardId}/good", produces = "application/json")
    public ResponseEntity<Board> addGoodCount (@PathVariable(value = "boardId", required = true) long boardId) {
        return ResponseEntity.ok().body(boardService.addGoodCount(boardId));
    }

    @DeleteMapping(path = "/{boardId}", produces = "application/json")
    public ResponseEntity<Board> deleteBorad (
            @PathVariable(value = "boardId", required = true) long boardId) {
        return ResponseEntity.ok().body(boardService.deleteBoard(boardId));
    }
}
