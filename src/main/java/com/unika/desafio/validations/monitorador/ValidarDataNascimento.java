package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.TipoPessoa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class ValidarDataNascimento implements IMonitoradorValid{
    @Override
    public void validar(RequestPessoaDto requestDto) {
        dataNascimentoValid(requestDto.getDataNascimento());
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA) idadeMenor18(requestDto.getDataNascimento());
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        dataNascimentoValid(requestDto.getDataNascimento());
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA) idadeMenor18(requestDto.getDataNascimento());
    }

    private void dataNascimentoValid(LocalDate dataNascimento){
        if (dataNascimento == null) throw new BusinessException("Data de nascimento é obrigatória!");
        if (dataNascimento.isAfter(LocalDate.now())) throw new BusinessException("A data de nascimento precisa ser uma data passada!");
    }

    private void idadeMenor18(LocalDate dataNascimento){
        int idade = calcularIdade(dataNascimento);
        if (idade < 18) throw new BusinessException("Pessoas físicas devem ter mais de 18 anos para se cadastrar!");
    }

    private int calcularIdade(final LocalDate dataNascimento) {
        final LocalDate dataAtual = LocalDate.now();
        final Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears();
    }
}
