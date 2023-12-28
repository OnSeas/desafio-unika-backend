package com.unika.desafio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "ESTAGIO_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO", nullable = false)
    private Long id;

    @Column(name = "ENDERECO", nullable = false)
    private String endereco;

    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "TELEFONE", nullable = false)
    private String telefone;

    @Column(name = "CIDADE", nullable = false)
    private String cidade;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "PRINCIPAL", nullable = false)
    private Boolean principal;

    @ManyToOne
    @JsonBackReference
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
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

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", endereco='" + endereco + '\'' +
                ", numero='" + numero + '\'' +
                ", cep=" + cep +
                ", bairro='" + bairro + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", principal=" + principal +
                ", monitorador=" + monitorador +
                '}';
    }
}
