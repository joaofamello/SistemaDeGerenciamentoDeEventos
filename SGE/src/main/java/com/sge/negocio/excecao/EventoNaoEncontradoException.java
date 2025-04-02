package com.sge.negocio.excecao;

public class EventoNaoEncontradoException extends SGEException {

    public EventoNaoEncontradoException(String Titulo) {
        super("Evento não encontrado: " + Titulo);
    }
}
