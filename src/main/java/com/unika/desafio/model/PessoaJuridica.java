package com.unika.desafio.model;

import com.unika.desafio.mask.Mask;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PessoaJuridica extends Monitorador{

    @Column(name = "CNPJ", nullable = false)
    private String cnpj;

    @Setter
    @Getter
    @Column(name = "RAZAO_SOCIAL", nullable = false)
    private String razaoSocial;

    @Setter
    @Getter
    @Column(name = "INSCRICAO_ESTADUAL", nullable = false)
    private String inscricaoEstadual;

    public String getCnpj() {
        return Mask.applyMaskCNPJ(this.cnpj);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = Mask.removeMaskCNPJ(cnpj);
    }



    @Override
    public String toString() {
        return "PessoaJuridica{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                '}';
    }
}
