package com.unika.desafio.repository;

import com.unika.desafio.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Endereco findByMonitoradorIdAndPrincipal(Long id, boolean principal);

    Integer countByMonitoradorIdAndPrincipal(Long id, boolean principal);

    Integer countByMonitoradorId(Long idMonitor);

    List<Endereco> findAllByMonitoradorId(Long id);
}
