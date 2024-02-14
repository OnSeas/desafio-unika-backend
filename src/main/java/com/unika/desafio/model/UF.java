package com.unika.desafio.model;

import lombok.Getter;

@Getter
public enum UF {
    AMAZONAS("AM"),
    ALAGOAS("AL"),
    ACRE("AC"),
    AMAPA("AP"),
    BAHIA("BA"),
    PARA("PA"),
    MATO_GROSSO("MT"),
    MINAS_GERAIS("MG"),
    MATO_GROSSO_DO_SUL("MS"),
    GO("GO"), // TODO
    MARANHAO("MA"),
    RIO_GRANDE_DO_SUL("RS"),
    TOCANTINS("TO"),
    PIAUI("PI"),
    SAO_PAULO("SP"),
    RONDONIA("RO"),
    RORAIMA("RR"),
    PARANA("PR"),
    CEARA("CE"),
    PERNAMBUCO("PE"),
    SANTA_CATARINA("SC"),
    PARAIBA("PB"),
    RIO_GRANDE_DO_NORTE("RN"),
    ESPIRITO_SANTO("ES"),
    RIO_DE_JANEIRO("RJ"),
    SERGIPE("SE"),
    DISTRITO_FEDERAL("DF");

    private final String value;

    UF(String value){
        this.value = value;
    }
}
