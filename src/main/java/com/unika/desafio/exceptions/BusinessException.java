package com.unika.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public BusinessException(ErrorCode errorCode){
        super();
        this.message = errorCode.message;
        this.status = errorCode.status;
    }

    public BusinessException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus(){
        return this.status;
    }
}
