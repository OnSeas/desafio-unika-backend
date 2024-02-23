package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;

public interface IMonitoradorValid {

    // Criar
    void validar(RequestPessoaDto requestDto);

    // Editar
    void validar(RequestPessoaDto requestDto, Long id);
}
