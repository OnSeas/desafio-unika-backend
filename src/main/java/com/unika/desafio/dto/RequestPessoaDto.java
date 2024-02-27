package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.unika.desafio.model.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPessoaDto {

    TipoPessoa tipoPessoa;

    String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    LocalDate dataNascimento;

    String cpf;

    String rg;

    String nome;

    String cnpj;

    String razaoSocial;

    String inscricaoEstadual;

    List<RequestEnderecoDto> enderecoList;

   // Para validações de repetição no Banco de dados
   public String getCpfSemFormatacao(){
       return this.cpf.replaceAll("[.]", "").replaceAll("[-]", "");
   }

    public String getCnpjSemFormatacao(){
        return this.cnpj.replaceAll("[.]", "").replaceAll("[/]", "").replaceAll("[-]", "");
    }

    public String getRGSemFormatacao(){
        return this.rg.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    public void setTipoPessoaInt(Integer value){
       this.tipoPessoa = TipoPessoa.getTipo(value);
    }

    @Override
    public String toString() {
        return "RequestPessoaDto{" +
                "tipoPessoa=" + tipoPessoa +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", inscricaoEstadual='" + inscricaoEstadual + '\'' +
                ", enderecoList=" + enderecoList +
                '}';
    }
}
