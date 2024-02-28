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
        CPF("cpf"),
        CNPJ("cnpj"),
        EMAIL("email");

        private final String label;

        TipoBusca(String label){
            this.label = label;
        }
    }
}


