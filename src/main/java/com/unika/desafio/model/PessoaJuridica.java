package com.unika.desafio.model;

import jakarta.persistence.*;

@Entity
public class PessoaJuridica extends Monitorador{

    @Column(name = "CNPJ", nullable = false)
    private String cnpj;

    @Column(name = "RAZAO_SOCIAL", nullable = false)
    private String razaoSocial;

    @Column(name = "INSCRICAO_ESTADUAL", nullable = false)
    private String inscricaoEstadual;

    public String getCnpj() {
        return aplicarMascaraCnpj(cnpj);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = removerFormacataoCnpj(cnpj);
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    private String removerFormacataoCnpj(String cnpj) {
        return cnpj.replaceAll("[.]", "").replaceAll("[/]", "").replaceAll("[-]", "");
    }

    private String aplicarMascaraCnpj(String cnpj){
        StringBuilder cnpjBuilder = new StringBuilder(cnpj);
        cnpjBuilder.insert(12, "-");
        cnpjBuilder.insert(8, "/");
        cnpjBuilder.insert(5, ".");
        cnpjBuilder.insert(2, ".");
        return cnpjBuilder.toString();
    }

    @Override
    public String toString() {
        return "PessoaJuridica{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                '}';
    }
}
