package com.sge.negocio.excecao;

public class NenhumEventoCriadoException extends SGEException {

    public NenhumEventoCriadoException() {
        super("Você não possui nenhum evento cadastrado");
    }
}
