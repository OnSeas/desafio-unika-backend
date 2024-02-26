package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarInscricaoEstadual implements IMonitoradorValid{

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            inscricaoEstadualValid(requestDto.getInscricaoEstadual());
            if(repository.findByInscricaoEstadual(requestDto.getInscricaoEstadual()) != null){
                throw new BusinessException(ErrorCode.INSCRICAO_ESTADUAL_REPETIDA);
            }
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            inscricaoEstadualValid(requestDto.getInscricaoEstadual());
            if(repository.findByDifferentInscricaoEstadual(requestDto.getInscricaoEstadual(), id) != null){
                throw new BusinessException(ErrorCode.INSCRICAO_ESTADUAL_REPETIDA);
            }
        }

    }

    private void inscricaoEstadualValid(String inscricaoEstadual){
        if (inscricaoEstadual == null || inscricaoEstadual.isBlank()) throw new BusinessException(ErrorCode.REQUISICAO_PJ_INVALIDA);
        String inscricaoSemMascaras = inscricaoEstadual.replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "");
        if (inscricaoEstadual.length() > 18 || inscricaoEstadual.length() < 7 || (!inscricaoSemMascaras.matches("[0-9]+"))) throw new BusinessException("Inscrição Estadual Inválida: Exemplos de inscrições estaduais validas: 01.427.625/777-48; 10.903.168-7; 775307865870.");
    }
}
