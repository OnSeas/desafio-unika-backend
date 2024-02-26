package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.Endereco;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarEnderecoList implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {

    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {

    }

    private void enderecoListValid(List<Endereco> enderecoList){
        if (enderecoList == null) throw new BusinessException("Lista de endereço não recebida!");
        if (enderecoList.isEmpty()) throw new BusinessException("É necessário enviar um endereço para cadastrar o monitorador");
    }
}
