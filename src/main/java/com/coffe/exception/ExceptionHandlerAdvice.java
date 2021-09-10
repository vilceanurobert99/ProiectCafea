package com.coffe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<HttpStatus> coffeeNotFoundExceptionHandler() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<HttpStatus> ingredientNotFoundExceptionHandler() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<HttpStatus> customerNotFoundExceptionHandler() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughIngredientsException.class)
    public ResponseEntity<HttpStatus> notEnoughIngredientsException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectCardException.class)
    public ResponseEntity<HttpStatus> incorrectCardExceptionHandler() {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
