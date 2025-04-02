package com.sge.negocio.excecao;

public class PermissaoNegadaException extends SGEException {
    public PermissaoNegadaException(String acao) {
        super("Permissão negada para: " + acao);
    }
}
