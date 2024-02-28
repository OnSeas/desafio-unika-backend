package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import org.springframework.stereotype.Component;

@Component
public class ValidarNome implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            nomeValid(requestDto.getNome());
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            nomeValid(requestDto.getNome());
        }
    }

    private void nomeValid(String nome){
        if (nome == null || nome.isBlank()) throw new BusinessException(ErrorCode.REQUISICAO_PF_INVALIDA);
        if (nome.length() < 3 || nome.length() > 30) throw new BusinessException("O nome deve ter entre 3 e 30 caracteres!");
        if (!nome.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*")) throw new BusinessException("O nome não pode conter números ou caracteres especiais!");
    }
}