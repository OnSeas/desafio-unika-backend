package com.unika.desafio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
public class PessoaFisica extends Monitorador{

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "RG", nullable = false)
    private String rg;

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

    public String getRG() {
        return rg;
    }

    public void setRG(String RG) {
        this.rg = RG;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", rg='" + rg + '\'' +
                '}';
    }
}
