package com.unika.desafio.validations.endereco;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.service.apisExternas.ConexaoViaCep;
import org.springframework.stereotype.Component;

@Component
public class ValidarCep implements IEnderecoValid{
    final ConexaoViaCep conexaoViaCep = new ConexaoViaCep();

    @Override
    public void validar(RequestEnderecoDto requestEnderecoDto) {
        cepValid(requestEnderecoDto.getCep());
    }

    private void cepValid(String cep){
        if (cep == null || cep.isBlank()) throw new BusinessException("Campo CEP não pode ser vazio");
        if ((cep.length() != 9 && cep.length() != 8) || (!cep.matches("\\d{5}-?\\d{3}"))) throw new BusinessException("CEP deve seguir o seguinte padrão: 00000-000");
        try { // A conexão deixa o cadastro muito mais lento
            conexaoViaCep.getEnderecoPeloCep(cep);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.CEP_INVALIDO);
        }
    }
}