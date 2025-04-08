package com.sge.negocio.excecao;

public class CidadeSemEventosException extends SGEException {

    public CidadeSemEventosException(String cidade) {
        super("Nenhum evento registrado na cidade: '" + cidade + "'");
    }

}
