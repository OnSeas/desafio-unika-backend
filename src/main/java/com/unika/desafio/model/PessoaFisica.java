package com.unika.desafio.model;

import com.unika.desafio.mask.Mask;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PessoaFisica extends Monitorador{

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Getter
    @Setter
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "RG", nullable = false)
    private String rg;

    public String getCpf() {
        return Mask.applyMaskCPF(this.cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = Mask.removeMaskCPF(cpf);
    }

    public String getRg() {
        return Mask.applyMaskRG(this.rg);
    }

    public void setRg(String rg) {
        this.rg = Mask.removeMaskRG(rg);
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", rg='" + rg + '\'' +
                '}';
    }
}
