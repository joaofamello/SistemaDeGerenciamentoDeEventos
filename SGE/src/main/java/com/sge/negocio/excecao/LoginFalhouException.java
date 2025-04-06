package com.sge.negocio.excecao;

public class LoginFalhouException extends SGEException {

    public LoginFalhouException() {
        super("Usuário ou senha incorretos");
    }
}
