package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarEnderecoList implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {
        enderecoListValid(requestDto.getEnderecoList());
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        enderecoListValid(requestDto.getEnderecoList());
    }

    private void enderecoListValid(List<RequestEnderecoDto> enderecoList){
        if (enderecoList == null || enderecoList.isEmpty()) throw new BusinessException("É necessário enviar um endereço para cadastrar o monitorador");
    }
}