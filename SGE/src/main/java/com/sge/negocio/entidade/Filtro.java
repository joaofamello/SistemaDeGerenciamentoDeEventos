package com.sge.negocio.entidade;
import com.sge.dados.eventos.RepositorioEventosArrayList;

import java.util.ArrayList;
import java.util.List;

public class Filtro {
    private static RepositorioEventosArrayList repositorio;

    public Filtro(RepositorioEventosArrayList repositorio) {
        this.repositorio = repositorio;
    }
    public static Evento buscarPorNome(String nome) {
        for(Evento evento : repositorio.getEventos()){
            if(evento.getTitulo().equalsIgnoreCase(nome)){
                return evento;
            }
        }
        return null;
    }

    public static List<Evento> buscarPorCategoria(String categoria) {
        List<Evento> eventosEncontrados = new ArrayList<>();
        for(Evento evento : repositorio.getEventos()){
            if(evento.getCategoria().equals(categoria)){
                eventosEncontrados.add(evento);
            }
        }
        return eventosEncontrados;
    }

    public static List<Evento> buscarPorCidade(String cidade) {
        List<Evento> eventosEncontrados = new ArrayList<>();
        for(Evento evento : repositorio.getEventos()){
            if(evento.getEndereco().getCidade().equalsIgnoreCase(cidade)){
                eventosEncontrados.add(evento);
            }
        }
        return eventosEncontrados;
    }

}
