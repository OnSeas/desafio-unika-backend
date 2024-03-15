package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.mask.Mask;
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
            if (repository.findbyrg(Mask.removeMaskRG(requestDto.getRg())) != null){
                throw new BusinessException(ErrorCode.RG_REPETIDO);
            }
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            rgValid(requestDto.getRg());
            if(repository.findByDifferentRg(Mask.removeMaskRG(requestDto.getRg()), id) != null){
                throw new BusinessException(ErrorCode.RG_REPETIDO);
            }
        }
    }

    private void rgValid(String rg){
        if (rg == null || rg.isBlank()) throw new BusinessException(ErrorCode.REQUISICAO_PF_INVALIDA);
        if (rg.length() > 12 || rg.length() < 9 || (!rg.matches("\\d{2}.?\\d{3}.?\\d{3}-?\\d"))) throw new BusinessException("O RG deve seguir o padrão 00.000.000-0");
        if (!isRG(Mask.removeMaskRG(rg))) throw new BusinessException("RG inválido!");;
    }

    private boolean isRG(String rg){
        if (rg.equals("000000000") ||
                rg.equals("111111111") ||
                rg.equals("222222222") || rg.equals("333333333") ||
                rg.equals("444444444") || rg.equals("555555555") ||
                rg.equals("666666666") || rg.equals("777777777") ||
                rg.equals("888888888") || rg.equals("999999999") ||
                (rg.length() != 9))
            return false;
        return true;
    }
}