package com.unika.desafio.controler;

import com.unika.desafio.dto.RequestEnderecoDto;
import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @PostMapping("/cadastrar/{idMonitor}")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody RequestEnderecoDto requestDto, @PathVariable Long idMonitor){
        try{
            ResponseEnderecoDto responseDto = service.cadastrarEndereco(requestDto, idMonitor);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
