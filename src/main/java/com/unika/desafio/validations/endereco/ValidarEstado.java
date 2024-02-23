package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.UF;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ValidarEstado implements IEnderecoValid{
    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        estadoValid(requestEnderecoDto.getEstado());
    }

    private void estadoValid(UF estado){
        if(estado == null) throw new BusinessException("Campo estado precisa ser preenchido");
    }
}
