package com.smuniov.telegrambot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BindException extends RuntimeException {

    public BindException(String message) {
        super(message);
    }
}
