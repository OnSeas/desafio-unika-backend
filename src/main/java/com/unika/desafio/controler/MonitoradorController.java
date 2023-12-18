package com.unika.desafio.controler;

import com.unika.desafio.dto.RequestPessoaDto;
import com.unika.desafio.dto.ResponsePessoaDto;
import com.unika.desafio.exceptions.ValidationException;
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
    public ResponseEntity<ResponsePessoaDto> cadastrarMonitorador(@RequestBody RequestPessoaDto requestDto){
        try{
            ResponsePessoaDto responseDto = service.cadastrarMonitorador(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (ValidationException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ResponsePessoaDto>> listarMonitoradores(){
        try {
            List<ResponsePessoaDto> responseList = service.listarMonitoradores();
            return ResponseEntity.ok(responseList);
        } catch (ValidationException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<ResponsePessoaDto> monitoradorPeloId(@PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = service.buscarPeloId(id);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (ValidationException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponsePessoaDto> atualizarMonitorador(@RequestBody RequestPessoaDto requestDto, @PathVariable Long id){
        try {
            ResponsePessoaDto responseDto = service.atualizarMonitorador(requestDto, id);
            return ResponseEntity.ok().body(responseDto);
        } catch (ValidationException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarMonitorador(@PathVariable Long id){
        try {
            service.deletarMonitorador(id);
            return ResponseEntity.ok().body("Monitorador Deletado com sucesso!");
        } catch (ValidationException e){
            return ResponseEntity.notFound().build();
        }
    }

}
