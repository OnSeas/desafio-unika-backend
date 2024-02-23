package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;

public interface IEnderecoValid {
    // Criar
    void validar(RequestEnderecoDto requestEnderecoDto);
}
