package com.board.springbd.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BoardException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public BoardException(String message) {
        super(message);
        logger.error(message);
    }
}
