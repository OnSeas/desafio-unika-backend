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

    @Override
    public String toString() {
        return "ResponsePessoaFisicaDto{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", id=" + id +
                ", tipoPessoa=" + tipoPessoa +
                ", email='" + email + '\'' +
                ", rg='" + rg + '\'' +
                ", inscricaoEstadual='" + inscricaoEstadual + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", enderecoList=" + enderecoList +
                '}';
    }
}
