package com.unika.desafio.model;

public enum TipoPessoa {
    PESSOA_FISICA(0),
    PESSOA_JURIDICA(1);

    final int codigo;

    TipoPessoa(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
