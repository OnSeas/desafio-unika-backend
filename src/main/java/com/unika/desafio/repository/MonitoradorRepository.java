package com.unika.desafio.repository;

import com.unika.desafio.model.Monitorador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoradorRepository extends JpaRepository<Monitorador, Long> {

}
