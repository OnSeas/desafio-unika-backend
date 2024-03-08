package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import org.springframework.stereotype.Component;

@Component
public class ValidarTipoPessoa implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {
        tipoPessoaValid(requestDto.getTipoPessoa());
    }


    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        tipoPessoaValid(requestDto.getTipoPessoa());
    }

    private void tipoPessoaValid(TipoPessoa tipoPessoa){
        if (tipoPessoa == null) throw new BusinessException("É necessário selecionar o tipo de pessoa!");
        if (!(tipoPessoa.equals(TipoPessoa.PESSOA_FISICA) || tipoPessoa.equals(TipoPessoa.PESSOA_JURIDICA))) throw new BusinessException(ErrorCode.TIPO_PESSOA_INVALIDO);
    }
}
