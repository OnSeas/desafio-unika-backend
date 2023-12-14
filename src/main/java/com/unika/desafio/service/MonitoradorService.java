package com.unika.desafio.service;

import com.unika.desafio.model.Monitorador;
import com.unika.desafio.repository.MonitoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MonitoradorService {
    @Autowired
    MonitoradorRepository repository;

    public Monitorador cadastrarMonitorador(Monitorador monitorador){
        return repository.save(monitorador);
    }

    public List<Monitorador> listarMonitoradores(){
        return repository.findAll();
    }

    public Monitorador atualizarMonitorador(Monitorador monitorador, Long id){
        Monitorador monitoradorDB = repository.getReferenceById(id);
        // Atualizar
        return repository.save(monitoradorDB);
    }

    public boolean deletarMonitorador(@PathVariable Long id){
        Monitorador monitorador = repository.getReferenceById(id);

        try {
            repository.delete(monitorador);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
