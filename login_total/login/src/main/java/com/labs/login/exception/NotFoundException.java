package com.labs.login.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger("[NOT FOUND EXCEPTION]");
    public NotFoundException(String msg) {
        super(msg);
        logger.info(msg);
    }
}
