package com.sge.negocio.excecao;

<<<<<<< Updated upstream
public class FormularioEventoInvalidoException extends SGEException {
    private final String campoInvalido;
    public FormularioEventoInvalidoException(String campoInvalido, String mensagem) {
      super("Erro no evento (campo '" + campoInvalido + "'): " + mensagem);
      this.campoInvalido = campoInvalido;
    }
=======
public class FormularioEventoInvalidoException extends SGEException{
  private final String campoInvalido;

  public FormularioEventoInvalidoException(String campoInvalido, String mensagem) {
    super("Erro no evento (campo '" + campoInvalido + "'): " + mensagem);
    this.campoInvalido = campoInvalido;
  }
>>>>>>> Stashed changes
}
