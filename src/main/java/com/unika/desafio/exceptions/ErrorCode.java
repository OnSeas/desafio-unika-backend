package com.unika.desafio.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MONITORADOR_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "O monitorador não foi encontrado."),
    EMAIL_REPETIDO(HttpStatus.BAD_REQUEST,"O email já foi cadastrado, tente buscar pelo monitorador ou utilizar outro email."),
    CPF_REPETIDO(HttpStatus.BAD_REQUEST,"O CPF já foi cadastrado, tente buscar pelo monitorador."),
    CNPJ_REPETIDO(HttpStatus.BAD_REQUEST,"O CNPJ já foi cadastrado, tente buscar pelo monitorador."),
    RG_REPETIDO(HttpStatus.BAD_REQUEST,"O RG já foi cadastrado, tente buscar pelo monitorador."),
    INSCRICAO_ESTADUAL_REPETIDA(HttpStatus.BAD_REQUEST,"A Inscricao Estadual já foi cadastrado, tente buscar pelo monitorador."),
    CAMPOS_VAZIOS(HttpStatus.BAD_REQUEST,"Há campos obrigatórios vazios.");

    HttpStatus status;
    String message;

    ErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
