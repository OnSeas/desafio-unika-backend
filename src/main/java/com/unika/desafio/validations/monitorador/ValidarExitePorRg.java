package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarExitePorRg implements IMonitoradorJaExiste{

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (repository.existsByRG(requestDto.getRg())){
            throw new BusinessException(ErrorCode.RG_REPETIDO);
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if(repository.findByDifferentRG(requestDto.getRg(), id) != null){
            throw new BusinessException(ErrorCode.RG_REPETIDO);
        }
    }
}
