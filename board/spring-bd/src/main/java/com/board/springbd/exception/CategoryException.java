package com.board.springbd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CategoryException extends RuntimeException{
    public CategoryException(String message) {
        super(message);
    }
}
