package com.unika.desafio.controler;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.service.MonitoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitorador")
public class MonitoradorController {
    @Autowired
    private MonitoradorService service;
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMonitorador(@RequestBody RequestPessoaDto requestDto){
        try{
            ResponsePessoaDto responseDto = service.cadastrarMonitorador(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMonitoradores(){
        try {
            List<ResponsePessoaDto> responseList = service.listarMonitoradores();
            return new ResponseEntity<>(responseList, HttpStatus.FOUND);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> monitoradorPeloId(@PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = service.buscarPeloId(id);
            return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMonitorador(@RequestBody RequestPessoaDto requestDto, @PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = service.atualizarMonitorador(requestDto, id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMonitorador(@PathVariable Long id){
        try {
            service.deletarMonitorador(id);
            return new ResponseEntity<>("Monitorador Deletado com sucesso!", HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

}
