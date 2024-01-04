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
        this.cpf = removerFormatacaoCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRG() {
        return aplicarMascaraRg(rg);
    }

    public void setRG(String rg) {
        this.rg = removerFormatacaoRg(rg);
    }


    // Máscaras de CPF
    private String removerFormatacaoCpf(String cpf) {
        return cpf.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    private String aplicarMascaraCpf(String cpf){
        StringBuilder cpfBuilder = new StringBuilder(cpf);
        cpfBuilder.insert(9, "-");
        cpfBuilder.insert(6, ".");
        cpfBuilder.insert(3, ".");
        return cpfBuilder.toString();
    }

    private String removerFormatacaoRg(String rg){
        return rg.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    private String aplicarMascaraRg(String rg){
        StringBuilder rgBuilder = new StringBuilder(rg);
        rgBuilder.insert(8, "-");
        rgBuilder.insert(5, ".");
        rgBuilder.insert(2,".");
        return rgBuilder.toString();
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
