package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.mask.Mask;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class ValidarCPf implements IMonitoradorValid{
    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            cpfInputValid(requestDto.getCpf());
            if(repository.findByCpf(Mask.removeMaskCPF(requestDto.getCpf())) != null){
                throw new BusinessException(ErrorCode.CPF_REPETIDO);
            }
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            cpfInputValid(requestDto.getCpf());
            if(repository.findByDifferentCpf(Mask.removeMaskCPF(requestDto.getCpf()), id) != null){
                throw new BusinessException(ErrorCode.CPF_REPETIDO);
            }
        }
    }

    private void cpfInputValid(String cpf){
        if(cpf == null || cpf.isBlank()) throw new BusinessException(ErrorCode.REQUISICAO_PF_INVALIDA);
        if(cpf.length() > 14 || cpf.length() < 11 || (!cpf.matches("\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}"))) throw new BusinessException("O CPF precisa seguir o padrão 000.000.000-00");
        if (!isCPF(Mask.removeMaskCPF(cpf))) throw new BusinessException("CPF Inválido!");
    }

    // Validador genérico de cpf - fonte:https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF"s formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere "0" no inteiro 0
                // (48 eh a posicao de "0" na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
