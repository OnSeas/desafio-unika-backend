package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarRg implements IMonitoradorValid{

    @Autowired
    private MonitoradorRepository repository;
    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            rgValid(requestDto.getRg());
            if (repository.findbyrg(requestDto.getRGSemFormatacao()) != null){
                throw new BusinessException(ErrorCode.RG_REPETIDO);
            }
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            rgValid(requestDto.getRg());
            if(repository.findByDifferentRg(requestDto.getRGSemFormatacao(), id) != null){
                throw new BusinessException(ErrorCode.RG_REPETIDO);
            }
        }
    }

    private void rgValid(String rg){
        if (rg == null || rg.isBlank()) throw new BusinessException(ErrorCode.REQUISICAO_PF_INVALIDA);
        if (rg.length() != 12 || (!rg.matches("\\d{2}.\\d{3}.\\d{3}-\\d"))) throw new BusinessException("O RG deve seguir o padrão 00.000.000-0");
        // Validar de acordo com o código validador?
    }
}