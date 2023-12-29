package com.unika.desafio.model;

import jakarta.persistence.*;

@Entity
public class PessoaFisica extends Monitorador{

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "RG", nullable = false)
    private String rg;

    public String getCpf() {
        return aplicarMascaraCpf(cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = removerFormacataoCpf(cpf);
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


    // Máscaras de CPF
    private String removerFormacataoCpf(String cpf) {
        return cpf.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    private String aplicarMascaraCpf(String cpf){
        StringBuilder cpfBuilder = new StringBuilder(cpf);
        cpfBuilder.insert(9, "-");
        cpfBuilder.insert(6, ".");
        cpfBuilder.insert(3, ".");
        return cpfBuilder.toString();
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
