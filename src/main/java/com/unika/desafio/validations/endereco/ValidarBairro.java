package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ValidarBairro implements IEnderecoValid{
    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        bairroValid(requestEnderecoDto.getBairro());
    }
    private void bairroValid(String bairro){
        if (bairro == null || bairro.isBlank()) throw new BusinessException("Campo bairro não pode ser vazio");
        if (bairro.length() < 3 || bairro.length() > 20) throw new BusinessException("Campo bairro deve ter entre 3 e 20 caracteres");
        if (!bairro.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*")) throw new BusinessException("Bairro não pode conter números ou caracteres especiais!");
    }
}