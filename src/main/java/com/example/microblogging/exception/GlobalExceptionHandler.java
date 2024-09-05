package com.example.microblogging.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(BaseException.class)
   public ResponseEntity<String> handleBaseException(BaseException ex) {
      log.error(ex.getMessage());
      return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
      log.error(ex.getMessage());
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(TweetTooLongException.class)
   public ResponseEntity<String> handleTweetTooLongException(TweetTooLongException ex) {
      log.error(ex.getMessage());
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<String> handleException(RuntimeException ex) {
      log.error(ex.getMessage());
      return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
   }

}