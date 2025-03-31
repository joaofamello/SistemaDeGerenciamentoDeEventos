package com.sge.negocio.entidade;

public class Filtro {
    public static Evento buscarPorNome(String nome) {
        for(Evento evento : RepositorioEventosArrayList){
            if(evento.getTitulo().equalsIgnoreCase(nome)){
                return evento;
            }
        }
    }

    public static Evento buscarPorNome(String categoria) {
        for(Evento evento : RepositorioEventosArrayList){
            if(evento.getCategoria().equals(categoria)){
                return evento;
            }
        }
    }

    public static Evento buscarPorNome(String cidade) {
        for(Evento evento : RepositorioEventosArrayList){
            if(evento.getEndereco().getCidade().equalsIgnoreCase(cidade)){
                return evento;
            }
        }
    }
}
