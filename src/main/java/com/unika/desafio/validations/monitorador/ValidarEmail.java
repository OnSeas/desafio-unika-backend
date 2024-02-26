package com.unika.desafio.validations.monitorador;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidarEmail implements IMonitoradorValid{

    @Autowired
    private MonitoradorRepository repository;

    @Override
    public void validar(RequestPessoaDto requestDto) {
        emailValido(requestDto.getEmail());
        if (repository.existsByEmail(requestDto.getEmail())){
            throw new BusinessException(ErrorCode.EMAIL_REPETIDO);
        }
    }

    @Override
    public void validar(RequestPessoaDto requestDto, Long id) {
        emailValido(requestDto.getEmail());
        if (repository.findByDifferentEmail(requestDto.getEmail(), id) != null){
            throw new BusinessException(ErrorCode.EMAIL_REPETIDO);
        }
    }

    private void emailValido(String email){
        if(email == null || email.isBlank()) throw new BusinessException("O email não pode ser vazio.");
        if(email.length() > 50) throw new BusinessException("O email deve ter no máximo 50 caracteres!");

        if (!patternMatches(email, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) throw new BusinessException("Email inválido!");

    }

    // fonte: https://www.baeldung.com/java-email-validation-regex
    private boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}