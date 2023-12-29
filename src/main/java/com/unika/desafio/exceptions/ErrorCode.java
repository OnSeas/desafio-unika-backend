package com.unika.desafio.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MONITORADOR_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "O monitorador não foi encontrado."),
    EMAIL_REPETIDO(HttpStatus.BAD_REQUEST,"O email já foi cadastrado, tente buscar pelo monitorador ou utilizar outro email."),
    CPF_REPETIDO(HttpStatus.BAD_REQUEST,"O CPF já foi cadastrado, tente buscar pelo monitorador."),
    CNPJ_REPETIDO(HttpStatus.BAD_REQUEST,"O CNPJ já foi cadastrado, tente buscar pelo monitorador."),
    RG_REPETIDO(HttpStatus.BAD_REQUEST,"O RG já foi cadastrado, tente buscar pelo monitorador."),
    INSCRICAO_ESTADUAL_REPETIDA(HttpStatus.BAD_REQUEST,"A Inscricao Estadual já foi cadastrado, tente buscar pelo monitorador."),
    ENDERECO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND,"O endereco não foi encontrado."),
    MONITOR_SEM_ENDERECOS(HttpStatus.NOT_FOUND,"O monitor não possuí nenhum endereço."),
    MONITORADOR_SEM_ENDERECO_PRINCIPAL(HttpStatus.NOT_FOUND,"O monitorador não possuí nenhum endereço principal."),
    ENDERECO_PRINCIPAL_UNICO(HttpStatus.BAD_REQUEST,"Apenas um endereço pode ser selecionado como principal."),
    REQUISICAO_PF_INVALIDA(HttpStatus.BAD_REQUEST,"Ao enviar uma pessoa física é necessário enviar: CPF, RG e nome."),
    REQUISICAO_PJ_INVALIDA(HttpStatus.BAD_REQUEST, "Ao enviar uma pessoa juridíca é necessário enviar: CNPJ, inscricao estadual e razão Social."),
    TIPO_PESSOA_INVALIDO(HttpStatus.BAD_REQUEST,"O tipo de pessoa enviado não é válido!"),
    MONITORADOR_JA_ATIVO(HttpStatus.BAD_REQUEST,"O monitorador já está ativo!"),
    MONITORADOR_JA_DESATIVADO(HttpStatus.BAD_REQUEST,"O monitorador já está desativado!"),
    ENDERECO_JA_E_PRINCIPAL(HttpStatus.BAD_REQUEST,"Este já é o endereco principal do monitorador!"),
    ENDERECO_NAO_E_DO_MONITORADOR(HttpStatus.NOT_FOUND,"Este endereço não pertence ao monitorador solicitado!"),
    NENHUM_MONITORADOR_POR_EMAIL(HttpStatus.NOT_FOUND,"Não há pessoa com este email!"),
    PESSOA_POR_CPF(HttpStatus.NOT_FOUND,"Não há pessoa com este CPF!"),
    PESSOA_POR_CNPJ(HttpStatus.NOT_FOUND,"Não há pessoa com este CNPJ!"),
    CEP_INVALIDO(HttpStatus.NOT_FOUND,"Este CEP não foi encontrado, tem certeza que informou o CEP correto?");

    HttpStatus status;
    String message;

    ErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
