package com.example.microblogging.exception;

public class TweetTooLongException extends BaseException {

    public TweetTooLongException() {
        super("El tweet no puede exceder los 280 caracteres.");
    }
}
