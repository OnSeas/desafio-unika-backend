package com.unika.desafio.controler;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.Monitorador;
import com.unika.desafio.service.EnderecoService;
import com.unika.desafio.service.MonitoradorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitorador")
public class MonitoradorController {
    @Autowired
    private MonitoradorService monitoradorService;

    @Autowired
    private EnderecoService enderecoService;

    ModelMapper mapper = new ModelMapper();

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMonitorador(@RequestBody RequestPessoaDto requestDto){
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
            return new ResponseEntity<>(responseList, HttpStatus.FOUND);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> monitoradorPeloId(@PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = monitoradorService.getMonitorResponsePeloId(id);
            return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMonitorador(@RequestBody RequestPessoaDto requestDto, @PathVariable Long id){
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
    public ResponseEntity<?> cadastrarEndereco(@PathVariable Long idMonitorador, @RequestBody RequestEnderecoDto requestDto){
        try {
            Monitorador monitorador = monitoradorService.pegarMonitorPeloId(idMonitorador);
            ResponseEnderecoDto responseDto = enderecoService.cadastrarEndereco(monitorador, requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}