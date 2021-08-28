package com.labs.login.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger("[BAD REQUEST EXCEPTION]");
    public BadRequestException(String msg) {
        super (msg);
        logger.error(msg);
    }
}
