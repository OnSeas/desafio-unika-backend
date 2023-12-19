package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;

public interface IMonitoradorJaExiste {
    void validar(RequestPessoaDto requestDto);

    void validar(RequestPessoaDto requestDto, Long id);
}
