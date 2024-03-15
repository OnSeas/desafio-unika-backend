package com.unika.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unika.desafio.mask.Mask;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ESTAGIO_ENDERECO")
public class Endereco {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO", nullable = false)
    private Long id;

    @Setter
    @Getter
    @Column(name = "ENDERECO", nullable = false)
    private String endereco;

    @Setter
    @Getter
    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Setter
    @Getter
    @Column(name = "BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "TELEFONE", nullable = false)
    private String telefone;

    @Setter
    @Getter
    @Column(name = "CIDADE", nullable = false)
    private String cidade;

    @Setter
    @Getter
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ESTADO", nullable = false)
    private UF estado;

    @Setter
    @Getter
    @Column(name = "PRINCIPAL", nullable = false)
    private Boolean principal;

    @Setter
    @Getter
    @ManyToOne
    @JsonBackReference
    private Monitorador monitorador;

    public String getCep() {
        return Mask.apllyMaskCEP(this.cep);
    }

    public void setCep(String cep) {
        this.cep = Mask.removeMaskCEP(cep);
    }

    public String getTelefone() {
        return Mask.apllyMaskTelefone(this.telefone);
    }

    public void setTelefone(String telefone) {
        this.telefone = Mask.removeMaskTelefone(telefone);
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
