package com.sge.negocio.excecao;

public class SenhaInvalidaException extends SGEException {
    private final int tamanhoMinimo;

    public SenhaInvalidaException(int tamanhoMinimo) {
        super("A senha deve ter no mínimo " + tamanhoMinimo + " caracteres");
        this.tamanhoMinimo = tamanhoMinimo;
    }

    //metodo para comunicar que a senha precisa de no minimo 5 caracteres
    public String getRequisitosSenha() {
        return "Requisitos: mínimo de " + tamanhoMinimo + " caracteres";
    }
}
