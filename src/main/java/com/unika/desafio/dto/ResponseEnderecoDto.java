package com.unika.desafio.dto;

import com.unika.desafio.model.Monitorador;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseEnderecoDto {
    Long id;
    String endereco;
    String numero;
    // Implementar mascára
    String cep;
    String bairro;
    // Implementar mascára
    String Telefone;
    String cidade;
    String estado;
    Boolean principal;
    Long monitoradorId; // TODO não precisa

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
