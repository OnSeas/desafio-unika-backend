package com.unika.desafio.controler;

import com.unika.desafio.model.Monitorador;
import com.unika.desafio.service.MonitoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitorador")
public class MonitoradorController {
    @Autowired
    MonitoradorService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarMonitorador(@RequestBody Monitorador monitorador){
        try{
            service.cadastrarMonitorador(monitorador);
            return ResponseEntity.ok().body("Monitorador Cadastrado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Monitorador>> listarMonitoradores(){
        try {
            List<Monitorador> monitoradorList = service.listarMonitoradores();
            return ResponseEntity.ok(monitoradorList);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarMonitorador(@RequestBody Monitorador monitorador, @PathVariable Long id){
        try {
            service.atualizarMonitorador(monitorador, id);
            return ResponseEntity.ok().body("Monitorador atualizado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarMonitorador(@PathVariable Long id){
        try {
            service.deletarMonitorador(id);
            return ResponseEntity.ok().body("Monitorador Deletado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
