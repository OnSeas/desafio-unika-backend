package com.unika.desafio.service;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.dto.ResponsePessoaFisicaDto;
import com.unika.desafio.dto.ResponsePessoaJuridicaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.*;
import com.unika.desafio.repository.MonitoradorRepository;
import com.unika.desafio.validations.monitorador.IMonitoradorJaExiste;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonitoradorService {
    @Autowired
    private MonitoradorRepository repository;

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private List<IMonitoradorJaExiste> monitoradorJaExisteValidations;

    public ResponsePessoaDto cadastrarMonitorador(RequestPessoaDto requestDto){

        // Validando se já existe
        monitoradorJaExisteValidations.forEach(v -> v.validar(requestDto));
        dadosEstaoVazios(requestDto);
        Monitorador monitorador = getMonitoradorByTipo(requestDto);
        monitorador.ativar();
        return getResponseByTipo(repository.save(monitorador));
    }

    public List<ResponsePessoaDto> listarMonitoradores(){
        List<Monitorador> monitoradorList = repository.findAll();
        return monitoradorList.
                stream()
                .map(this::getResponseByTipo)
                .collect(Collectors.toList());
    }

    public ResponsePessoaDto buscarPeloId(Long id) {
        monitoradorExitePeloId(id);
        return getResponseByTipo(repository.getReferenceById(id));
    }

    public ResponsePessoaDto atualizarMonitorador(RequestPessoaDto requestDto, Long id){
        monitoradorExitePeloId(id);

        monitoradorJaExisteValidations.forEach(v -> v.validar(requestDto, id)); // Validar se tem outro monitorador com informações que não pode ser repetidas

        Monitorador monitorador = getMonitoradorByTipo(requestDto);
        Monitorador monitoradorDB = repository.getReferenceById(id);

        BeanUtils.copyProperties(
                monitorador,
                monitoradorDB,
                "id", "ativo");

        return getResponseByTipo(repository.save(monitoradorDB));
    }

    public void deletarMonitorador(@PathVariable Long id){
        monitoradorExitePeloId(id);
        repository.deleteById(id);
    }

    public Monitorador adcionarEndereco(Endereco endereco, Long id){
        monitoradorExitePeloId(id);
        Monitorador monitorador = repository.getReferenceById(id);
        monitorador.adicionarEndereco(endereco);
        return repository.save(monitorador);
    }

    // MÉTODOS ENCAPSULADOS
    private Monitorador getMonitoradorByTipo(RequestPessoaDto requestDto){
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            return mapper.map(requestDto, PessoaFisica.class);
        } else{
            return mapper.map(requestDto, PessoaJuridica.class);
        }
    }

    private ResponsePessoaDto getResponseByTipo(Monitorador monitorador){
        if(monitorador.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            return mapper.map(monitorador, ResponsePessoaFisicaDto.class);
        } else{
            return mapper.map(monitorador, ResponsePessoaJuridicaDto.class);
        }
    }

    // VALIDACOES
    private void monitoradorExitePeloId(Long id){
        if(!repository.existsById(id))
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }

    private void dadosEstaoVazios(RequestPessoaDto requestDto){
        if(false) // TODO Melhor maneira de achar se tem algum valor vazio?
            throw new BusinessException(ErrorCode.CAMPOS_VAZIOS);
    }
}
