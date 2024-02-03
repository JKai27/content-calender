package com.example.contentcalender.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidContentOrContentNotFoundException extends RuntimeException {
    public InvalidContentOrContentNotFoundException(String message) {
        super(message);
    }
}
