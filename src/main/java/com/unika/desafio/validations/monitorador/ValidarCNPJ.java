package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import org.springframework.stereotype.Component;

@Component
public class ValidarCNPJ implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {

    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {

    }
}
