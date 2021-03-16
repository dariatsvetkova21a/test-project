package com.rincentral.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalRequestParameterValue extends RuntimeException {
    public IllegalRequestParameterValue(String msg) {
        super(msg);
    }
}
