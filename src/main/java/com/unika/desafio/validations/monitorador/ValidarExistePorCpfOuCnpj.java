package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarExistePorCpfOuCnpj implements IMonitoradorJaExiste{

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            if(repository.findByCpf(requestDto.getCpf()) != null){
                throw new BusinessException(ErrorCode.CPF_REPETIDO);
            }
        }else{
            if(repository.findByCnpj(requestDto.getCnpj()) != null){
                throw new BusinessException(ErrorCode.CNPJ_REPETIDO);
            }
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            if(repository.findByDifferentCpf(requestDto.getCpf(), id) != null){
                throw new BusinessException(ErrorCode.CPF_REPETIDO);
            }
        }else{
            if(repository.findByDifferentCnpj(requestDto.getCnpj(), id) != null){
                throw new BusinessException(ErrorCode.CNPJ_REPETIDO);
            }
        }
    }
}
