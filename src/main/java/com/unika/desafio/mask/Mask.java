package com.unika.desafio.mask;

public class Mask {
    public static String applyMaskCPF(String cpf){
        StringBuilder cpfBuilder = new StringBuilder(cpf);
        cpfBuilder.insert(9, "-");
        cpfBuilder.insert(6, ".");
        cpfBuilder.insert(3, ".");
        return cpfBuilder.toString();
    }

    public static String removeMaskCPF(String cpf){
        return cpf.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    public static String applyMaskRG(String rg){
        StringBuilder rgBuilder = new StringBuilder(rg);
        rgBuilder.insert(8, "-");
        rgBuilder.insert(5, ".");
        rgBuilder.insert(2,".");
        return rgBuilder.toString();
    }

    public static String removeMaskRG(String rg){
        return rg.replaceAll("[.]", "").replaceAll("[-]", "");
    }

    public static String applyMaskCNPJ(String cnpj){
        StringBuilder cnpjBuilder = new StringBuilder(cnpj);
        cnpjBuilder.insert(12, "-");
        cnpjBuilder.insert(8, "/");
        cnpjBuilder.insert(5, ".");
        cnpjBuilder.insert(2, ".");
        return cnpjBuilder.toString();

    }

    public static String removeMaskCNPJ(String cnpj){
        return cnpj.replaceAll("[.]", "").replaceAll("[/]", "").replaceAll("[-]", "");
    }

    public static String apllyMaskCEP(String cep){
        StringBuilder cepBuilder = new StringBuilder(cep);
        cepBuilder.insert(5, "-");
        return cepBuilder.toString();
    }

    public static String removeMaskCEP(String cep){
        return cep.replaceAll("[-]", "");
    }

    public static String removeMaskTelefone(String telefone){
        return telefone.replaceAll("[(]", "").replaceAll("[)]", "").replaceAll("[-]", "");
    }

    public static String apllyMaskTelefone(String telefone){
        StringBuilder telefoneBuilder = new StringBuilder(telefone);
        telefoneBuilder.insert(telefone.length()-4, "-");
        telefoneBuilder.insert(2, ")");
        telefoneBuilder.insert(0, "(");
        return telefoneBuilder.toString();
    }

}