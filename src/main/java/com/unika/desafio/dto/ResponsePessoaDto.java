package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unika.desafio.model.Endereco;
import com.unika.desafio.model.TipoPessoa;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ResponsePessoaDto {
    Long id;
    TipoPessoa tipoPessoa;
    String email;
    String rg;
    String inscricaoEstadual;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    LocalDate dataNascimento;
    List<Endereco> enderecoList;
    String cpf;
    String nome;
    String cnpj;
    String razaoSocial;

    @Override
    public String toString() {
        return "ResponsePessoaDto{" +
                "id=" + id +
                ", tipoPessoa=" + tipoPessoa +
                ", email='" + email + '\'' +
                ", rg='" + rg + '\'' +
                ", inscricaoEstadual='" + inscricaoEstadual + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", enderecoList=" + enderecoList +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                '}';
    }
}
