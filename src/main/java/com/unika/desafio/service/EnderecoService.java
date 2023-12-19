package com.unika.desafio.service;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.model.Endereco;
import com.unika.desafio.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private MonitoradorService monitoradorService;

    private ModelMapper mapper = new ModelMapper();

    public ResponseEnderecoDto cadastrarEndereco(RequestEnderecoDto requestDto, Long id){ // TODO Arrumar
        Endereco endereco = mapper.map(requestDto, Endereco.class);
        System.out.println(endereco.getEndereco());
        endereco.setMonitorador(monitoradorService.adcionarEndereco(endereco, id));
        repository.save(endereco);
        return mapper.map(endereco, ResponseEnderecoDto.class);
    }
}
