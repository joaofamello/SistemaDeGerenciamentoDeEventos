package com.sge.negocio.entidade;
import com.sge.dados.eventos.RepositorioEventosArrayList;

import java.util.ArrayList;
import java.util.List;

public class Filtro {
    private static RepositorioEventosArrayList repositorio;

    public Filtro() {
        Filtro.repositorio = new RepositorioEventosArrayList();
    }

    public static Evento buscarPorTitulo(String Titulo) {
        for(Evento evento : repositorio.getEventos()){
            if(evento.getTitulo().equalsIgnoreCase(Titulo)){
                return evento;
            }
        }
        return null;
    }

    public static List<Evento> buscarPorCategoria(String categoria) {
        List<Evento> eventos = new ArrayList<>();
        for(Evento evento : repositorio.getEventos()){
            if(evento.getCategoria().equalsIgnoreCase(categoria)){
                eventos.add(evento);
            }
        }
        return eventos;
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
