package com.unika.desafio.service;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.PessoaFisica;
import com.unika.desafio.model.PessoaJuridica;
import com.unika.desafio.model.TipoPessoa;
import com.unika.desafio.repository.MonitoradorRepository;
import com.unika.desafio.validations.monitorador.IMonitoradorJaExiste;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonitoradorService {
    @Autowired
    private MonitoradorRepository repository;

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private List<IMonitoradorJaExiste> monitoradorJaExiste;

    public ResponsePessoaDto cadastrarMonitorador(RequestPessoaDto requestDto){
        monitoradorJaExiste.forEach(v -> v.validar(requestDto));// Validando se já existe
        Monitorador monitorador = getMonitoradorByTipo(requestDto);
        monitorador.ativar();
        return mapper.map(repository.save(monitorador), ResponsePessoaDto.class);
    }

    public List<ResponsePessoaDto> listarMonitoradores(){
        List<Monitorador> monitoradorList = repository.findAll();
        return monitoradorList.
                stream()
                .map(m -> mapper.map(m, ResponsePessoaDto.class))
                .collect(Collectors.toList());
    }

    public ResponsePessoaDto getMonitoradorResponsePeloId(Long id) {
        return mapper.map(pegarMonitoradorPeloId(id), ResponsePessoaDto.class);
    }

    public Monitorador pegarMonitoradorPeloId(Long id){
        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if (optionalMonitorador.isPresent()){
            Monitorador monitorador = optionalMonitorador.get();
            System.out.println("Monitorador BD: " + monitorador.getClass());
            return repository.getReferenceById(id);
        } else {
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        }
    }

    public ResponsePessoaDto atualizarMonitorador(RequestPessoaDto requestDto, Long id){
        monitoradorJaExiste.forEach(v -> v.validar(requestDto, id)); // Validar se tem outro monitorador com informação que não pode ser repetida

        Optional<Monitorador> optionalMonitorador = repository.findById(id);
        if (optionalMonitorador.isPresent()){
            Monitorador monitoradorDB = optionalMonitorador.get();

            Monitorador monitorador = getMonitoradorByTipo(requestDto);
            System.out.println(monitorador);

            BeanUtils.copyProperties(
                    monitorador,
                    monitoradorDB,
                    "id", "ativo");

            System.out.println(monitoradorDB);

            return mapper.map(repository.save(monitoradorDB), ResponsePessoaDto.class);
        } else {
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
        }
    }

    public void deletarMonitorador(Long id){
        monitoradorExiste(id);
        repository.deleteById(id);
    }

    // MÉTODOS ENCAPSULADOS
    private Monitorador getMonitoradorByTipo(RequestPessoaDto requestDto){
        isRequisisaoValida(requestDto);
        if(requestDto.getTipoPessoa() == TipoPessoa.PESSOA_FISICA){
            return mapper.map(requestDto, PessoaFisica.class);
        } else{
            return mapper.map(requestDto, PessoaJuridica.class);
        }
    }

    // Validacoes
    public void monitoradorExiste(Long id){
        if(!repository.existsById(id))
            throw new BusinessException(ErrorCode.MONITORADOR_NAO_ENCONTRADO);
    }

    private void isRequisisaoValida(RequestPessoaDto requestDto){
        switch (requestDto.getTipoPessoa()){
            case PESSOA_FISICA:
                if(requestDto.getCpf() == null || requestDto.getNome() == null || requestDto.getRg() == null)
                    throw new BusinessException(ErrorCode.REQUISICAO_PF_INVALIDA);
                break;
            case PESSOA_JURIDICA:
                if (requestDto.getCnpj() == null || requestDto.getRazaoSocial() == null || requestDto.getInscricaoEstadual() == null || requestDto.getInscricaoEstadual().isBlank())
                    throw new BusinessException(ErrorCode.REQUISICAO_PJ_INVALIDA);
                break;
            default:
                throw new BusinessException(ErrorCode.TIPO_PESSOA_INVALIDO);
        }
    }
}
