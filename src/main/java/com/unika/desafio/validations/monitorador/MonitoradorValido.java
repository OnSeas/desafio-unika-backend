package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class MonitoradorValido implements IMonitoradorValid {
    @Override
    public void validar(RequestPessoaDto requestDto) {
        requisicaoValida(requestDto);
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        requisicaoValida(requestDto);
    }

    private void requisicaoValida(RequestPessoaDto requestDto){
        if(requestDto.getEnderecoList() == null || requestDto.getEnderecoList().isEmpty()) if(requestDto.getEnderecoList().isEmpty()) throw new BusinessException(ErrorCode.SEM_ENDERECOS);
        else throw new BusinessException(ErrorCode.SEM_ENDERECOS);
        if(requestDto.getEmail() == null || requestDto.getEmail().isBlank()) throw new BusinessException("O email não pode ser vazio.");
        if(requestDto.getDataNascimento() == null) throw new BusinessException("A data de nascimento não pode ser vazia.");
        if(requestDto.getTipoPessoa() == null) throw new BusinessException("O tipo de pessoa não pode ser vazio.");

        requisicaoValidaByTipo(requestDto);
    }

    private void requisicaoValidaByTipo(RequestPessoaDto requestDto) {
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
