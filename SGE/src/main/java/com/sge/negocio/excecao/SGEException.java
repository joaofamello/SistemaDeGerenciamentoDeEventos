package com.sge.negocio.excecao;

public class SGEException extends Exception {
    public SGEException(String message) {
        super(message);
    }


    public String getDetalhes() {
        return "Erro no Sistema de Gerenciamento de Eventos: " + getMessage();
    }
}
