package com.sge.negocio.excecao;

public class FormularioUsuarioInvalidoException extends SGEException {
    private final String campoInvalido;
    public FormularioUsuarioInvalidoException(String campoInvalido, String mensagem) {
        super("Erro no campo '" + campoInvalido + "': " + mensagem);
        this.campoInvalido = campoInvalido;
    }


}
