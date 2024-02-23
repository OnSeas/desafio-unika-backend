package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class ValidarTelefone implements IEnderecoValid{
    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        telefoneValid(requestEnderecoDto.getTelefone());
    }

    private void telefoneValid(String telefone){
        if (telefone == null || telefone.isBlank()) throw new BusinessException("Campo telefone não pode ser vazio");
        if (telefone.length() < 14 || telefone.length() > 15 || (!telefone.matches("\\(\\d{2}\\)\\d?\\d{4}-\\d{4}")))
            throw new BusinessException("O campo telefone tem que seguir o padrão (00)00000-0000 ou (00)0000-0000");
    }
}