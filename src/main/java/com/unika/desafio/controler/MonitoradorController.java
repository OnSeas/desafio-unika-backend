package com.unika.desafio.controler;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.service.EnderecoService;
import com.unika.desafio.service.MonitoradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monitorador")
public class MonitoradorController {
    @Autowired
    private MonitoradorService monitoradorService;

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMonitorador(@RequestBody @Valid RequestPessoaDto requestDto){
        try{
            ResponsePessoaDto responseDto = monitoradorService.cadastrarMonitorador(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMonitoradores(){
        try {
            List<ResponsePessoaDto> responseList = monitoradorService.listarMonitoradores();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> monitoradorPeloId(@PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = monitoradorService.getMonitoradorResponsePeloId(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMonitorador(@RequestBody @Valid RequestPessoaDto requestDto, @PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = monitoradorService.atualizarMonitorador(requestDto, id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMonitorador(@PathVariable Long id){
        try {
            monitoradorService.deletarMonitorador(id);
            return new ResponseEntity<>("Monitorador Deletado com sucesso!", HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("/{idMonitorador}/endereco")
    @Transactional
    public ResponseEntity<?> cadastrarEndereco(@PathVariable Long idMonitorador, @RequestBody @Valid RequestEnderecoDto requestDto){
        try {
            Monitorador monitorador = monitoradorService.pegarMonitoradorPeloId(idMonitorador);
            ResponseEnderecoDto responseDto = enderecoService.cadastrarEndereco(monitorador, requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/ativar/{idMonitorador}")
    public ResponseEntity<?> ativarMonitorador(@PathVariable Long idMonitorador){
        try {
            monitoradorService.ativarMonitorador(idMonitorador);
            return new ResponseEntity<>("O monitorador foi ativado!", HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/desativar/{idMonitorador}")
    public ResponseEntity<?> desativarMonitorador(@PathVariable Long idMonitorador){
        try {
            monitoradorService.desativarMonitorador(idMonitorador);
            return new ResponseEntity<>("O monitorador foi desativado!", HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("buscar/email/{email}")
    public ResponseEntity<?> monitoradorPeloEmail(@PathVariable String email){
        try {
            ResponsePessoaDto responseDto = monitoradorService.buscarPorEmail(email);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("buscar/cpf/{cpf}")
    public ResponseEntity<?> monitoradorPeloCpf(@PathVariable String cpf){
        try {
            ResponsePessoaDto responseDto = monitoradorService.buscarPorCpf(cpf);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
    @GetMapping("buscar/cnpj/{cnpj}")
    public ResponseEntity<?> monitoradorPeloCnpj(@PathVariable String cnpj){
        try {
            ResponsePessoaDto responseDto = monitoradorService.buscarPorCnpj(cnpj);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    // Pegar exceção de @Valid e retornar Response // TODO extrair para classe
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}