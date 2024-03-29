package com.example.GreenAtom.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * Класс исключения приложения
 */
public abstract class AppException extends RuntimeException{
    private Logger logger = LoggerFactory.getLogger(AppException.class);
    protected final LocalDateTime timestamp;
    protected final Integer status;
    protected final String message;

    protected AppException(Integer status, String message){
        this.status = status;
        this.message = message;
        timestamp = LocalDateTime.now();
        logger.warn(message);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
