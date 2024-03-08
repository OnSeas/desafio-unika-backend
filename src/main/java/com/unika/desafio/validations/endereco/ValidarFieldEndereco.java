package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ValidarFieldEndereco implements IEnderecoValid{
    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        fieldEnderecoValid(requestEnderecoDto.getEndereco());
    }

    private void fieldEnderecoValid(String endereco){
        if (endereco == null || endereco.isBlank()) throw new BusinessException("Campo endereço não pode ser vazio");
        if(endereco.length() < 3 || endereco.length() > 30) throw new BusinessException("Campo endereço deve ter entre 3 e 30 caracteres");
    }
}