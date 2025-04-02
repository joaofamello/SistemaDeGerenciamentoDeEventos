package com.sge.negocio.excecao;

public class UsuarioInvalidoException extends SGEException {
    public UsuarioInvalidoException(String message) {
        super("Usuario inválido: " + message);
    }
}
