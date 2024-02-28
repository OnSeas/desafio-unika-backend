package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ValidarCidade implements IEnderecoValid{
    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        cidadeValid(requestEnderecoDto.getCidade());
    }

    private void cidadeValid(String cidade){
        if (cidade == null || cidade.isBlank()) throw new BusinessException("Campo cidade não pode ser vazio");
        if (cidade.length() < 3 || cidade.length() > 20) throw new BusinessException("Campo bairro deve ter entre 3 e 20 caracteres");
        if (!cidade.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*")) throw new BusinessException("Cidade não pode conter números ou caracteres especiais!");
    }
}