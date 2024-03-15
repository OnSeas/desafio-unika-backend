package com.unika.desafio.filtros;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroMonitoradorDTO {
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
}


