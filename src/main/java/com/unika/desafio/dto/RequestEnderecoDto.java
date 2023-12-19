package com.unika.desafio.dto;

public class RequestEnderecoDto {
    String endereco;
    String numero;
    String cep;
    String bairro;
    String Telefone;
    String cidade;
    String estado;
    Boolean principal;

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getTelefone() {
        return Telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Boolean getPrincipal() {
        return principal;
    }
}
