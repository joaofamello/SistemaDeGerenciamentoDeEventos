package com.sge.negocio.excecao;

public class IngressosEsgotadoException extends SGEException {
    public IngressosEsgotadoException(String evento) {
        super("Ingressos esgotados para o evento: " + evento);
    }
}
