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
    private int cep;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "PRINCIPAL")
    private Boolean principal;

    @ManyToOne
    private Monitorador monitorador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Monitorador getMonitorador() {
        return monitorador;
    }

    public void setMonitorador(Monitorador monitorador) {
        this.monitorador = monitorador;
    }
}
