package com.unika.desafio.service;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.dto.ResponsePessoaFisicaDto;
import com.unika.desafio.dto.ResponsePessoaJuridicaDto;
import com.unika.desafio.exceptions.ValidationException;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.PessoaFisica;
import com.unika.desafio.model.PessoaJuridica;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoradorService {
    @Autowired
    private MonitoradorRepository repository;

    private ModelMapper mapper = new ModelMapper();

    public ResponsePessoaDto cadastrarMonitorador(RequestPessoaDto requestDto){
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
        if(repository.existsById(id)){
            return getResponseByTipo(repository.findById(id).get());
        }
            throw new ValidationException("Monitorador não encontrado", 404);
    }

    public ResponsePessoaDto atualizarMonitorador(RequestPessoaDto requestDto, Long id){
        Monitorador monitorador = getMonitoradorByTipo(requestDto);
        Monitorador monitoradorDB = repository.getReferenceById(id);

        BeanUtils.copyProperties(
                monitorador,
                monitoradorDB,
                "id", "ativo");

        return getResponseByTipo(repository.save(monitoradorDB));
    }

    public void deletarMonitorador(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new ValidationException("Monitorador não encontrado!", 404);
        }
    }

    // MÉTODOS ENCAPSULADOS
    private Monitorador getMonitoradorByTipo(RequestPessoaDto requestDto){
        if (requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            return mapper.map(requestDto, PessoaFisica.class);
        } else if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            return mapper.map(requestDto, PessoaJuridica.class);
        } else {
            throw new ValidationException("Tipo de pessoa não reconhecido.", 400);
        }
    }

    private ResponsePessoaDto getResponseByTipo(Monitorador monitorador){
        if (monitorador.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            return mapper.map(monitorador, ResponsePessoaFisicaDto.class);
        } else if(monitorador.getTipoPessoa() == TipoPessoa.PESSOA_JURIDICA){
            return mapper.map(monitorador, ResponsePessoaJuridicaDto.class);
        } else {
            throw new ValidationException("Tipo de pessoa não reconhecido.", 400);
        }
    }
}
