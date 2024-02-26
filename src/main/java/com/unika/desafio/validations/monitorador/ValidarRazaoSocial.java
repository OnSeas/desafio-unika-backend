package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import org.springframework.stereotype.Component;

@Component
public class ValidarRazaoSocial implements IMonitoradorValid{

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            razaoSocialValid(requestDto.getRazaoSocial());
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            razaoSocialValid(requestDto.getRazaoSocial());
        }
    }

    private void razaoSocialValid(String razaoSocial){
        if (razaoSocial == null || razaoSocial.isBlank()) throw new BusinessException(ErrorCode.REQUISICAO_PJ_INVALIDA);
        if (razaoSocial.length() < 3 || razaoSocial.length() > 50) throw new BusinessException("A razão social deve ter entre 3 e 50 caracteres!");
    }
}