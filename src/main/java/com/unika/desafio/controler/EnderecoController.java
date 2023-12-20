package com.unika.desafio.controler;

import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.Endereco;
import com.unika.desafio.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @GetMapping("/principal/{idMonitor}")
    public ResponseEntity<?> getEnderecoPrincipal(@PathVariable Long idMonitor){
        try {
            ResponseEnderecoDto responseDto = service.getEnderecoPrincipal(idMonitor);
            return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/listar/{idMonitor}")
    public ResponseEntity<?> getEnderecosMonitor(@PathVariable Long idMonitor){
        try {
            List<ResponseEnderecoDto> responseDtoList = service.listarEnderecosMonitorResponse(idMonitor);
            return new ResponseEntity<>(responseDtoList, HttpStatus.FOUND);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarEndereco(@PathVariable Long id, @RequestBody Endereco endereco){
        try {
            ResponseEnderecoDto responseDto = service.editarEndereco(id, endereco);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEndereco(@PathVariable Long id){
        try {
            service.deletarEndereco(id);
            return new ResponseEntity<>("Endereco deletado", HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


}
