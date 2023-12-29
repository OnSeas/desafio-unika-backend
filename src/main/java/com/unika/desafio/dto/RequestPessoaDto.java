package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unika.desafio.model.TipoPessoa;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestPessoaDto {

    @NotNull(message = "O tipo de pessoa não pode ser vazio.")
    TipoPessoa tipoPessoa;

    @NotBlank(message = "O email não pode ser vazio.")
    @Email(message = "Email inválido!")
    String email;

    @NotNull(message = "A data de nascimento não pode ser vazia.")
    @Past
    LocalDate dataNascimento;

    @CPF(message = "O CPF informado é inválido!")
    String cpf;

    @Pattern(regexp = "\\d{9}")
    String rg;

    @Size(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres!")
    String nome;

    @CNPJ(message = "O CNPJ informado é inválido!")
    String cnpj;

    @Size(min = 3, max = 200, message = "A razão social deve ter entre 3 e 200 caracteres!")
    String razaoSocial;

   // TODO Descobrir como funciona
   @Pattern(regexp = "\\d{14}")
    String inscricaoEstadual;


   // Para validações de repetição no Banco de dados
   public String getCpfSemFormatacao(){
       return this.cpf.replaceAll("[.]", "").replaceAll("[-]", "");
   }

    public String getCnpjSemFormatacao(){
        return this.cnpj.replaceAll("[.]", "").replaceAll("[/]", "").replaceAll("[-]", "");
    }
}
