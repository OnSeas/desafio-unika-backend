package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarExistePorEmail implements IMonitoradorJaExiste{

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (repository.existsByEmail(requestDto.getEmail())){
            throw new BusinessException(ErrorCode.EMAIL_REPETIDO);
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (repository.findByDifferentEmail(requestDto.getEmail(), id) != null){
            throw new BusinessException(ErrorCode.EMAIL_REPETIDO);
        }
    }
}
