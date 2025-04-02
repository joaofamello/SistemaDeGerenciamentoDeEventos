package com.sge.negocio.excecao;

public class UsuarioNaoEncontradoException extends SGEException {

    public UsuarioNaoEncontradoException(String nomeUsuario) {
        super("Usuário não encontrado: " + nomeUsuario);
    }

    @Override
    public String getDetalhes() {
        return "Falha na busca: " + getMessage() + " (verifique se o nome está correto)";
    }
}
