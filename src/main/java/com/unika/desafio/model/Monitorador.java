package com.unika.desafio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // O tipo de herança vai definir quais tabelas vão ser criadas no banco
@Table(name = "ESTAGIO_MONITORADOR")
public abstract class Monitorador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MONITORADOR", nullable = false)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TIPO_PESSOA", nullable = false)
    private TipoPessoa tipoPessoa;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private Date dataNascimento;

    @Column(name = "ATIVO", nullable = false)
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
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
