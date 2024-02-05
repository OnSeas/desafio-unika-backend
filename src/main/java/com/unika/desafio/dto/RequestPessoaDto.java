package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unika.desafio.model.TipoPessoa;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    LocalDate dataNascimento;

    @CPF(message = "O CPF informado é inválido!")
    String cpf;

    @Pattern(regexp = "\\d{2}.?\\d{3}.?\\d{3}-?\\d", message = "O RG informado é inválido!")
    String rg;

    @Size(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres!")
    String nome;

    @CNPJ(message = "O CNPJ informado é inválido!")
    String cnpj;

    @Size(min = 3, max = 200, message = "A razão social deve ter entre 3 e 200 caracteres!")
    String razaoSocial;

    @Size(min = 7, max = 18, message = "Tamanho para inscrição estadual inválido!")
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
                '}';
    }
}
