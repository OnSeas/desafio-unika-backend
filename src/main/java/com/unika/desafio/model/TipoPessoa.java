package com.unika.desafio.model;

import com.unika.desafio.exceptions.BusinessException;
import com.unika.desafio.exceptions.ErrorCode;

public enum TipoPessoa {
    PESSOA_FISICA(0),
    PESSOA_JURIDICA(1);

    final int codigo;

    TipoPessoa(int codigo){
        this.codigo = codigo;
    }

    public static TipoPessoa getTipo(int codigo){
        if(codigo == 0){
            return TipoPessoa.PESSOA_FISICA;
        } else if (codigo == 1){
            return TipoPessoa.PESSOA_JURIDICA;
        } else throw new BusinessException(ErrorCode.TIPO_PESSOA_INVALIDO);
    }

    public int getCodigo() {
        return codigo;
    }
}
