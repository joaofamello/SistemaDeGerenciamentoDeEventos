package com.sge.negocio.excecao;

public class EmailJaExistenteException extends SGEException {
    public EmailJaExistenteException() {
        super("Email já cadastrado no sistema.");
    }
}
