package com.unika.desafio.model;

import jakarta.persistence.*;
import org.springframework.expression.spel.ast.Identifier;

@Entity
@Table(name = "ESTAGIO_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "CEP")
    private int Cep;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "PRINCIPAL")
    private boolean principal;

    @ManyToOne
    private Monitorador monitorador;
}
