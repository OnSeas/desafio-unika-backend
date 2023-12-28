package com.unika.desafio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestEnderecoDto {

    @NotBlank
    String endereco;

    @NotBlank
    String numero;

    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}")
    String cep;

    @NotBlank
    String bairro;

    @NotBlank
    @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
    // Implementar mascára
    String Telefone;

    @NotBlank
    String cidade;

    @NotBlank
    String estado;

    @NotNull
    Boolean principal;

}
