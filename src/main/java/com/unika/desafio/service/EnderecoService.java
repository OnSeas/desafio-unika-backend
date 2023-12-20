package com.unika.desafio.service;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.Endereco;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.repository.EnderecoRepository;
import com.unika.desafio.service.apisExternas.ConexaoViaCep;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private MonitoradorService monitoradorService;

    private ModelMapper mapper = new ModelMapper();

    private ConexaoViaCep conexaoViaCep;

    public ResponseEnderecoDto cadastrarEndereco(Monitorador monitorador, RequestEnderecoDto requestDto){
        monitoradorService.monitoradorExiste(monitorador.getId());

        Endereco endereco = mapper.map(requestDto, Endereco.class);
        endereco.setMonitorador(monitorador);

        if(endereco.getPrincipal()){
            temOutroEnderecoPrincipal(monitorador.getId());
        }

        repository.save(endereco);
        return mapper.map(endereco, ResponseEnderecoDto.class);
    }

    public List<ResponseEnderecoDto> listarEnderecosMonitorResponse(Long id){
        List<Endereco> enderecoList = listarEnderecosMonitor(id);
        return enderecoList
                .stream()
                .map(e -> mapper.map(e, ResponseEnderecoDto.class))
                .collect(Collectors.toList());
    }

    public List<Endereco> listarEnderecosMonitor(Long id){
        monitoradorService.monitoradorExiste(id);
        temAlgumEndereco(id);

        return repository.getReferenceByMonitoradorId(id);
    }

    public ResponseEnderecoDto getEnderecoPrincipal(Long id){
        monitoradorService.monitoradorExiste(id);
        temAlgumEndereco(id);

        return mapper.map(repository.findByMonitoradorIdAndPrincipal(id, true), ResponseEnderecoDto.class);
    }

    public ResponseEnderecoDto editarEndereco(Long idEndereco, Endereco endereco){
        enderecoExite(idEndereco);
        Endereco enderecoDB = repository.getReferenceById(idEndereco);

        if(endereco.getPrincipal()){
            temOutroEnderecoPrincipal(endereco.getMonitorador().getId());
        }

        BeanUtils.copyProperties(
                endereco,
                enderecoDB,
                "id", "monitorador");

        return mapper.map(repository.save(enderecoDB), ResponseEnderecoDto.class);
    }

    public void deletarEndereco(Long idEndereco){
        enderecoExite(idEndereco);
        repository.deleteById(idEndereco);
    }

    // VALIDACOES
    private void temOutroEnderecoPrincipal(Long idMonitor){
        if(repository.countByMonitoradorIdAndPrincipal(idMonitor, true) > 0){
            throw new BusinessException(ErrorCode.ENDERECO_PRINCIPAL_UNICO);
        }
    }

    private void enderecoExite(Long idEndereco){
        if(!repository.existsById(idEndereco)){
            throw new BusinessException(ErrorCode.ENDERECO_NAO_ENCONTRADO);
        }
    }

    private void temAlgumEndereco(Long idMonitor){
        if(repository.countByMonitoradorId(idMonitor) == 0){
            throw new BusinessException(ErrorCode.MONITOR_SEM_ENDERECOS);
        }
    }
}