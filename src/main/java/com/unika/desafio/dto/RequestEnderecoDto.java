package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unika.desafio.model.UF;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestEnderecoDto {

    String endereco;

    String numero;

    String cep;

    String bairro;

    String Telefone;

    String cidade;

    UF estado;

    Boolean principal; // Usado no import de planilhas

}
