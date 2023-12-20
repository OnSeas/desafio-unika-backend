package com.unika.desafio.dto;

import com.unika.desafio.model.Monitorador;

public class ResponseEnderecoDto {
    Long id;
    String endereco;
    String numero;
    String cep;
    String bairro;
    String Telefone;
    String cidade;
    String estado;
    Boolean principal;
    Long monitoradorId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Long getId() {
        return id;
    }

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

    public Long getMonitoradorId() {
        return monitoradorId;
    }

    public void setMonitoradorId(Long monitoradorId) {
        this.monitoradorId = monitoradorId;
    }

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
