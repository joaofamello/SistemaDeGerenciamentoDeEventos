package com.sge.negocio.excecao;

public class FormularioEventoInvalidoException extends SGEException {
    private final String campoInvalido;
    public FormularioEventoInvalidoException(String campoInvalido, String mensagem) {
      super("Erro no evento (campo '" + campoInvalido + "'): " + mensagem);
      this.campoInvalido = campoInvalido;
    }
}
