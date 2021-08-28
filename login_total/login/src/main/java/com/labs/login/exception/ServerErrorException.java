package com.labs.login.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerErrorException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger("[SERVER ERROR EXCEPTION]");
    public ServerErrorException(String msg) {
        super(msg);
        logger.error(msg);
    }
}
