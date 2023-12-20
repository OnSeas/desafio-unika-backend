package com.unika.desafio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // O tipo de herança vai definir quais tabelas vão ser criadas no banco
@Table(name = "ESTAGIO_MONITORADOR")
public abstract class Monitorador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MONITORADOR")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TIPO_PESSOA")
    private TipoPessoa tipoPessoa;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "RG")
    private String RG;

    @Column(name = "INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @OneToMany(mappedBy = "monitorador", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Endereco> enderecoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Endereco> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList = enderecoList;
    }

    public void ativar(){
        this.ativo = Boolean.TRUE;
    }

    public void desativar(){
        this.ativo = Boolean.FALSE;
    }

    public void adicionarEndereco(Endereco endereco){
        enderecoList.add(endereco);
    }
}
