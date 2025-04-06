package com.sge.negocio.excecao;

public class UsernameJaExisteException extends SGEException {
    public UsernameJaExisteException() {
        super("Nome de Usuario já cadastrado no sistema");
    }
}
