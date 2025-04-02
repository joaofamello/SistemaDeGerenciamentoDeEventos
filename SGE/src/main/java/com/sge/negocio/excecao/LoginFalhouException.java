package com.sge.negocio.excecao;

public class LoginFalhouException extends SGEException {

    public LoginFalhouException() {
        super("E-mail ou senha incorretos");
    }
}
