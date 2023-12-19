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
    Monitorador monitorador;

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

    public void setMonitorador(Monitorador monitorador) {
        this.monitorador = monitorador;
    }
}
