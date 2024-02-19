package com.unika.desafio.controler;

import com.unika.desafio.dto.ResponseEnderecoDto;
import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.model.Endereco;
import com.unika.desafio.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @GetMapping("/principal/{idMonitor}")
    public ResponseEntity<?> getEnderecoPrincipal(@PathVariable Long idMonitor){
        try {
            ResponseEnderecoDto responseDto = service.getEnderecoPrincipal(idMonitor);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/buscar/{idEndereco}")
    public ResponseEntity<?> getEndereco(@PathVariable Long idEndereco){
        try {
            ResponseEnderecoDto responseDto = service.getEndereco(idEndereco);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/listar/{idMonitor}")
    public ResponseEntity<?> getEnderecosMonitor(@PathVariable Long idMonitor){
        try {
            List<ResponseEnderecoDto> responseDtoList = service.listarEnderecosMonitoradorResponse(idMonitor);
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarEndereco(@PathVariable Long id, @RequestBody @Valid Endereco endereco){
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

    @PutMapping("/{idMonitor}/tornar-principal/{idEndereco}")
    public ResponseEntity<?> tornarEnderecoPrincipal(@PathVariable Long idEndereco, @PathVariable Long idMonitor){
        try {
            service.tornarEnderecoPrincipal(idMonitor, idEndereco);
            return new ResponseEntity<>("O endereço foi tornado principal.", HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("getbyCep/{cep}")
    public ResponseEntity<?> getEnderecoByCep(@PathVariable String cep){
        try{
            ResponseEnderecoDto res = service.getEnderecoByCep(cep);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    // Pegar exceção de @Valid e retornar Response
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
