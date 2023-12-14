package com.unika.desafio.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ESTAGIO_MONITORADOR")
public class Monitorador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MONITORADOR")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TIPO_PESSOA")
    private TipoPessoa tipoPessoa;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "RG")
    private String RG;

    @Column(name = "INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "ATIVO")
    private boolean ativo;

    @OneToMany(mappedBy = "monitorador")
    private List<Endereco> enderecoList;
}
