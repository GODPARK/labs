package com.labs.login.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger("[UNAUTH EXCEPTION]");
    public UnAuthException(String msg) {
        super(msg);
        logger.info(msg);
    }
}
