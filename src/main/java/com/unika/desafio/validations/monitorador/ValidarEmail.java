package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ValidarEmail implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {
        emailValido(requestDto);
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        emailValido(requestDto);
    }

    private void emailValido(RequestPessoaDto requestDto){
        if(requestDto.getEmail().length() > 50) throw new BusinessException("O email deve ter no máximo 50 caracteres!");

    }
}
