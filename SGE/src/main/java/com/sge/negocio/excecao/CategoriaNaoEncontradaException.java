package com.sge.negocio.excecao;

public class CategoriaNaoEncontradaException extends SGEException {
    private final String categoria;
    public CategoriaNaoEncontradaException(String categoria) {
        super("Nenhum evento encontrado para a categoria: '" + categoria + "'");
        this.categoria = categoria;
    }
}
