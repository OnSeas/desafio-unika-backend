package com.unika.desafio.dto;

public class ResponsePessoaFisicaDto extends ResponsePessoaDto{
    String cpf;
    String nome;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
