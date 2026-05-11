package com.akayxn.cryptocurrencymarkettracker.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> allExceptionHandler(Exception e){
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
            return ResponseEntity.of(pd).build();
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> ResourceAlreadyExistHandler(ResourceAlreadyExistsException e){
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,e.getMessage());
            return ResponseEntity.of(pd).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> ResourceNotFoundHandler(ResourceNotFoundException e){
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
        return ResponseEntity.of(pd).build();
    }

}
