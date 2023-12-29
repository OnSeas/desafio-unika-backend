package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarExistePorInfoPessoa implements IMonitoradorJaExiste {

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            if(repository.findByCpf(requestDto.getCpfSemFormatacao()) != null){
                throw new BusinessException(ErrorCode.CPF_REPETIDO);
            }
            if (repository.findbyrg(requestDto.getRg()) != null){
                throw new BusinessException(ErrorCode.RG_REPETIDO);
            }
        }else{
            if(repository.findByCnpj(requestDto.getCnpjSemFormatacao()) != null){
                throw new BusinessException(ErrorCode.CNPJ_REPETIDO);
            }
            if(repository.findByInscricaoEstadual(requestDto.getInscricaoEstadual()) != null){
                throw new BusinessException(ErrorCode.INSCRICAO_ESTADUAL_REPETIDA);
            }
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            if(repository.findByDifferentCpf(requestDto.getCpfSemFormatacao(), id) != null){
                throw new BusinessException(ErrorCode.CPF_REPETIDO);
            }
            if(repository.findByDifferentRg(requestDto.getRg(), id) != null){
                throw new BusinessException(ErrorCode.RG_REPETIDO);
            }
        }else{
            if(repository.findByDifferentCnpj(requestDto.getCnpjSemFormatacao(), id) != null){
                throw new BusinessException(ErrorCode.CNPJ_REPETIDO);
            }
            if(repository.findByDifferentInscricaoEstadual(requestDto.getInscricaoEstadual(), id) != null){
                throw new BusinessException(ErrorCode.INSCRICAO_ESTADUAL_REPETIDA);
            }
        }
    }
}
