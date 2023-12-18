package com.unika.desafio.model;

import jakarta.persistence.*;

@Entity
public class PessoaFisica extends Monitorador{
    @Column(name = "CPF")
    private String cpf;

    @Column(name = "NOME")
    private String nome;

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
