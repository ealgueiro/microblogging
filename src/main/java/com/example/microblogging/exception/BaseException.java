package com.example.microblogging.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }
}
