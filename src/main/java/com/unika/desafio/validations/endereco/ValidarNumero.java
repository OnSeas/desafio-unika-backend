package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ValidarNumero implements IEnderecoValid{
    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        numeroValid(requestEnderecoDto.getNumero());
    }

    private void numeroValid(String num){
        if(num == null || num.isBlank()) throw new BusinessException("Campo número não pode ser vazio");
        if(num.length() > 5) throw new BusinessException("Campo número pode ter no máximo 5 caracteres");
    }
}