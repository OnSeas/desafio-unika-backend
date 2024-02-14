package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.UF;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseEnderecoDto {
    Long id;

    @JsonAlias(value = "logradouro")
    String endereco;
    String numero;
    String cep;
    String bairro;
    String Telefone;

    @JsonAlias(value = "localidade")
    String cidade;

    @JsonAlias(value = "uf")
    UF estado;
    Boolean principal;
    Long monitoradorId;

    @Override
    public String toString() {
        return "ResponseEnderecoDto{" +
                "id=" + id +
                ", endereco='" + endereco + '\'' +
                ", numero='" + numero + '\'' +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", Telefone='" + Telefone + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", principal=" + principal +
                ", monitorador=" + monitoradorId +
                '}';
    }
}
