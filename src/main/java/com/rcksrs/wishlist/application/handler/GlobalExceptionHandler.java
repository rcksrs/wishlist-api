package com.rcksrs.wishlist.application.handler;

import com.rcksrs.wishlist.domain.exception.shared.BusinessException;
import com.rcksrs.wishlist.domain.exception.shared.EntityNotFoundException;
import com.rcksrs.wishlist.domain.exception.shared.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ExceptionMessage>> handler(MethodArgumentNotValidException ex) {
        log.info("Exception caught while validating data. {}", ex.getMessage());

        List<ExceptionMessage> errors = ex.getFieldErrors()
                .stream()
                .map(e -> new ExceptionMessage(e.getDefaultMessage(), e.getField()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.info("Resource not found for the provided field. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handleBusinessException(BusinessException ex) {
        log.info("Unable to process the request. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionMessage> handleException(Exception ex) {
        log.error("Unexpected error. Unable to process the request.", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionMessage(ex.getMessage()));
    }
}
