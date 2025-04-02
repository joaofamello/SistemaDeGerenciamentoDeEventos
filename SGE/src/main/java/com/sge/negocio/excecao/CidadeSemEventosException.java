package com.sge.negocio.excecao;

public class CidadeSemEventosException extends SGEException {
    private final String cidade;
    public CidadeSemEventosException(String cidade) {
        super("Nenhum evento registrado na cidade: '" + cidade + "'");
        this.cidade = cidade;
    }

    //Sugerir para o usuario a busca por evento proximos da cidade que nao possui eventos
    public String getSugestoes(){
        return "Verifique eventos em cidades próximas a: " + this.cidade;
    }
}
