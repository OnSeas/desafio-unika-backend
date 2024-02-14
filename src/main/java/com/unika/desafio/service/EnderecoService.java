package com.unika.desafio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private MonitoradorService monitoradorService;

    private ModelMapper mapper = new ModelMapper();

    public ResponseEnderecoDto cadastrarEndereco(Monitorador monitorador, RequestEnderecoDto requestDto) throws IOException, InterruptedException {
        monitoradorService.monitoradorExiste(monitorador.getId());

        Endereco endereco = mapper.map(requestDto, Endereco.class);
        endereco.setMonitorador(monitorador);

        if(endereco.getPrincipal()){
            temOutroEnderecoPrincipal(monitorador.getId());
        }

        validarCepExiste(endereco.getCep());

        repository.save(endereco);
        return mapper.map(endereco, ResponseEnderecoDto.class);
    }

    public List<ResponseEnderecoDto> listarEnderecosMonitoradorResponse(Long id){
        List<Endereco> enderecoList = listarEnderecosMonitorador(id);
        return enderecoList
                .stream()
                .map(e -> mapper.map(e, ResponseEnderecoDto.class))
                .collect(Collectors.toList());
    }

    public List<Endereco> listarEnderecosMonitorador(Long id){
        monitoradorService.monitoradorExiste(id);
        temAlgumEndereco(id);

        return repository.findAllByMonitoradorId(id);
    }

    public ResponseEnderecoDto getEnderecoPrincipal(Long id){
        monitoradorService.monitoradorExiste(id);
        temAlgumEndereco(id);

        Endereco enderecoPrincipal = repository.findByMonitoradorIdAndPrincipalIsTrue(id);

        if(enderecoPrincipal== null)
            throw new BusinessException(ErrorCode.MONITORADOR_SEM_ENDERECO_PRINCIPAL);

        return mapper.map(enderecoPrincipal, ResponseEnderecoDto.class);
    }

    public ResponseEnderecoDto getEndereco(Long idEndereco) {
        Optional<Endereco> optionalEndereco = repository.findById(idEndereco);
        if(optionalEndereco.isPresent()){
            Endereco endereco = optionalEndereco.get();
            return mapper.map(endereco, ResponseEnderecoDto.class);
        } else {
            throw new BusinessException(ErrorCode.ENDERECO_NAO_ENCONTRADO);
        }
    }

    public ResponseEnderecoDto editarEndereco(Long idEndereco, Endereco endereco){
        Optional<Endereco> enderecoOptional = repository.findById(idEndereco);

        if (enderecoOptional.isPresent()){
            Endereco enderecoDB = enderecoOptional.get();
            BeanUtils.copyProperties(
                    endereco,
                    enderecoDB,
                    "id", "monitorador", "principal");

            return mapper.map(repository.save(enderecoDB), ResponseEnderecoDto.class);
        } else throw new BusinessException(ErrorCode.ENDERECO_NAO_ENCONTRADO);
    }

    public void deletarEndereco(Long idEndereco){
        Optional<Endereco> optionalEndereco = repository.findById(idEndereco);
        if (optionalEndereco.isPresent()){
            Endereco endereco = optionalEndereco.get();
            if (repository.countByMonitoradorId(endereco.getMonitorador().getId()) == 1){
                throw new BusinessException(ErrorCode.UNICO_ENDERECO);
            }
            repository.deleteById(idEndereco);
        } else throw new BusinessException(ErrorCode.ENDERECO_NAO_ENCONTRADO);

    }

    public void tornarEnderecoPrincipal(Long idMonitor, Long idEndereco){
        Optional<Endereco> enderecoOptional = repository.findById(idEndereco);
        if (enderecoOptional.isPresent()){
            Endereco novoEnderecoPrincipal = enderecoOptional.get();

            validarEndereco(novoEnderecoPrincipal, idMonitor);

            Endereco enderecoPrincipalAntigo = repository.findByMonitoradorIdAndPrincipalIsTrue(idMonitor);
            if (enderecoPrincipalAntigo != null){
                enderecoPrincipalAntigo.setPrincipal(Boolean.FALSE);
            }

            novoEnderecoPrincipal.setPrincipal(Boolean.TRUE);
            repository.save(novoEnderecoPrincipal);

        } else throw new BusinessException(ErrorCode.ENDERECO_NAO_ENCONTRADO);
    }

    public ResponseEnderecoDto getEnderecoByCep(String cep){
        try {
            ConexaoViaCep conexaoViaCep = new ConexaoViaCep();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseEnderecoDto endereco = objectMapper.readValue(conexaoViaCep.getEnderecoPeloCep(cep).body(), ResponseEnderecoDto.class);
            System.out.println(endereco);
            return endereco;
        } catch (Exception e){
            throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // VALIDACOES
    private void temOutroEnderecoPrincipal(Long idMonitor){
        if(repository.findByMonitoradorIdAndPrincipalIsTrue(idMonitor) != null){
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

    private void validarEndereco(Endereco endereco, Long idMonitor){
        if (endereco.getPrincipal()){
            if (Objects.equals(endereco.getMonitorador().getId(), idMonitor)){
                throw new BusinessException(ErrorCode.ENDERECO_JA_E_PRINCIPAL);
            } else throw new BusinessException(ErrorCode.ENDERECO_NAO_E_DO_MONITORADOR);
        }
    }

    private void validarCepExiste(String cep) throws IOException, InterruptedException {
        ConexaoViaCep conexaoViaCep = new ConexaoViaCep();
        HttpResponse<String> response = conexaoViaCep.getEnderecoPeloCep(cep);

        System.out.println(response);
        System.out.println(response.body());

        if (response.statusCode() == 400){
            throw new BusinessException(ErrorCode.CEP_INVALIDO);
        }

        if (response.body().contains("\"erro\": true")){
            throw new BusinessException(ErrorCode.CEP_INVALIDO);
        }

    }
}