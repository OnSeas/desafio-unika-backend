package com.unika.desafio.dto;

import com.unika.desafio.model.UF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestEnderecoDto {

    @NotBlank(message = "Campo endereço não pode ser vazio")
    @Size(min = 3, max = 50, message = "Campo endereço deve ter entre 3 e 50 caracteres")
    String endereco;

    @NotBlank(message = "Campo número não pode ser vazio")
    @Size(max = 5, message = "Campo número pode ter no máximo 5 caracteres")
    String numero;

    @NotBlank(message = "Campo CEP não pode ser vazio")
    @Pattern(regexp = "\\d{5}-\\d{3}")
    String cep;

    @NotBlank(message = "Campo bairro não pode ser vazio")
    @Size(min = 3, max = 20, message = "Campo bairro deve ter entre 3 e 20 caracteres")
    String bairro;

    @NotBlank(message = "Campo telefone não pode ser vazio")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}")
    String Telefone;

    @NotBlank(message = "Campo cidade não pode ser vazio")
    @Size(min = 3, max = 20, message = "Campo bairro deve ter entre 3 e 20 caracteres")
    String cidade;

    @NotNull(message = "Campo estado precisa ser preenchido")
    UF estado;

    @NotNull
    Boolean principal;

}
