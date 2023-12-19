package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarExistePorInscricaoEstadual implements IMonitoradorJaExiste{

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if(repository.existsByInscricaoEstadual(requestDto.getInscricaoEstadual())){
            throw new BusinessException(ErrorCode.INSCRICAO_ESTADUAL_REPETIDA);
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if(repository.findByDifferentInscricaoEstadual(requestDto.getInscricaoEstadual(), id) != null){
            throw new BusinessException(ErrorCode.INSCRICAO_ESTADUAL_REPETIDA);
        }
    }
}
