package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class MonitoradorValido implements IMonitoradorJaExiste{
    @Override
    public void validar(RequestPessoaDto requestDto) {
        requisicaoValida(requestDto);
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        requisicaoValida(requestDto);
    }

    private void requisicaoValida(RequestPessoaDto requestDto) {
        switch (requestDto.getTipoPessoa()){
            case PESSOA_FISICA:
                if(requestDto.getCpf() == null || requestDto.getNome() == null || requestDto.getRg() == null)
                    throw new BusinessException(ErrorCode.REQUISICAO_PF_INVALIDA);
                break;
            case PESSOA_JURIDICA:
                if (requestDto.getCnpj() == null || requestDto.getRazaoSocial() == null || requestDto.getInscricaoEstadual() == null || requestDto.getInscricaoEstadual().isBlank())
                    throw new BusinessException(ErrorCode.REQUISICAO_PJ_INVALIDA);
                break;
            default:
                throw new BusinessException(ErrorCode.TIPO_PESSOA_INVALIDO);
        }
    }
}
