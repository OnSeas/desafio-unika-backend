package com.unika.desafio.exceptions;

import org.springframework.http.ResponseEntity;

public class ValidationException extends RuntimeException{
        int statusCode;

        // TODO Organizar status code
        public ValidationException(String message, int statusCode){
            super(message);
            this.statusCode = statusCode;
        }
}
