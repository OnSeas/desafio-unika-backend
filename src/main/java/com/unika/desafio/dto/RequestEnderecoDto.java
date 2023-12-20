package com.unika.desafio.dto;

public class RequestEnderecoDto {
    //String endereco;
    String numero;
    String cep;
    //String bairro;
    String Telefone;
    //String cidade;
    //String estado;
    Boolean principal;

//    public String getEndereco() {
//        return endereco;
//    }

    public String getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

//    public String getBairro() {
//        return bairro;
//    }

    public String getTelefone() {
        return Telefone;
    }

//    public String getCidade() {
//        return cidade;
//    }
//
//    public String getEstado() {
//        return estado;
//    }

    public Boolean getPrincipal() {
        return principal;
    }

//    public void setEndereco(String endereco) {
//        this.endereco = endereco;
//    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

//    public void setBairro(String bairro) {
//        this.bairro = bairro;
//    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

//    public void setCidade(String cidade) {
//        this.cidade = cidade;
//    }
//
//    public void setEstado(String estado) {
//        this.estado = estado;
//    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
