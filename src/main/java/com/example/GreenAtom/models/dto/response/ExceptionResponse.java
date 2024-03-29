package com.example.GreenAtom.models.dto.response;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 */
public class ExceptionResponse {
    private final Integer status;
    private final LocalDateTime timesTamp;
    private final String message;

    public ExceptionResponse(Integer status, LocalDateTime timesTamp, String message) {
        this.status = status;
        this.timesTamp = timesTamp;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getTimesTamp() {
        return timesTamp;
    }

    public String getMessage() {
        return message;
    }
}
