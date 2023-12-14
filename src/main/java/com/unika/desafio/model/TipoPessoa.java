package com.unika.desafio.model;

public enum TipoPessoa {
    PESSOA_FISICA(1),
    PESSOA_JURIDICA(2);

    int codigo;

    TipoPessoa(int codico){
        this.codigo = codico;
    }

    public int getCodigo() {
        return codigo;
    }
}
