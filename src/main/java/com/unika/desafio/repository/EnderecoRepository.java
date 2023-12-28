package com.unika.desafio.repository;

import com.unika.desafio.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Endereco findByMonitoradorIdAndPrincipalIsTrue(Long id);

    Integer countByMonitoradorId(Long idMonitor);

    List<Endereco> findAllByMonitoradorId(Long id);
}
