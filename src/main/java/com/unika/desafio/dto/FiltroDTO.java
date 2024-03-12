package com.unika.desafio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroDTO {
    private String busca;
    private TipoBusca tipoBusca;
    private Boolean pessoaFisica;
    private Boolean pessoaJuridica;
    private Boolean soAtivados;

    @Getter
    public enum TipoBusca{
        CPF(0),
        CNPJ(1),
        EMAIL(2);

        private final int value;

        TipoBusca(int value){
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "FiltroDTO{" +
                "busca='" + busca + '\'' +
                ", tipoBusca=" + tipoBusca +
                ", pessoaFisica=" + pessoaFisica +
                ", pessoaJuridica=" + pessoaJuridica +
                ", soAtivados=" + soAtivados +
                '}';
    }
}


