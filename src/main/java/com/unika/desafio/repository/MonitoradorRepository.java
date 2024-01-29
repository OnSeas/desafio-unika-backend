package com.unika.desafio.repository;

import com.unika.desafio.model.Monitorador;
import com.unika.desafio.model.PessoaFisica;
import com.unika.desafio.model.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoradorRepository extends JpaRepository<Monitorador, Long> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf LIKE :cpf")
    Monitorador findByCpf(String cpf);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj LIKE :cnpj")
    Monitorador findByCnpj(String cnpj);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.rg LIKE :rg")
    Monitorador findbyrg(String rg);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscricaoEstadual LIKE :inscricaoEstadual")
    Monitorador findByInscricaoEstadual(String inscricaoEstadual);

    @Query(value = "SELECT m FROM Monitorador m WHERE m.email LIKE :email AND m.id != :id")
    Monitorador findByDifferentEmail(String email, Long id);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf LIKE :cpf AND pf.id != :id")
    Monitorador findByDifferentCpf(String cpf, Long id);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj LIKE :cnpj AND pj.id != :id")
    Monitorador findByDifferentCnpj(String cnpj, Long id);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.rg LIKE :rg AND pf.id != :id")
    Monitorador findByDifferentRg(String rg, Long id);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscricaoEstadual LIKE :inscricaoEstadual AND pj.id != :id")
    Monitorador findByDifferentInscricaoEstadual(String inscricaoEstadual, Long id);

    List<Monitorador> findByEmailContaining(String email);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.cpf ILIKE %:cpf%")
    List<Monitorador> findByCpfContaining(String cpf);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj ILIKE %:cnpj%")
    List<Monitorador> findByCnpjContaining(String cnpj);

    List<Monitorador> findByTipoPessoa(TipoPessoa tipoPessoa);

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.tipoPessoa = 0")
    List<PessoaFisica> findByAllPF();

    @Query(value = "SELECT pf FROM PessoaFisica pf WHERE pf.id = :id")
    Optional<PessoaFisica> findByIdPF(Long id);
}
