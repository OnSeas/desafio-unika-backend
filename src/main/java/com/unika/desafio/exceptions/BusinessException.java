package com.unika.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorCode.message;
    }

    public HttpStatus getStatus(){
        return this.errorCode.status;
    }
}
