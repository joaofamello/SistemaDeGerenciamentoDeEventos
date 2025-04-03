package com.sge.negocio.entidade;
import com.sge.dados.eventos.RepositorioEventosArrayList;

import java.util.ArrayList;
import java.util.List;

public class Filtro {
    private static RepositorioEventosArrayList repositorio;

    public Filtro(RepositorioEventosArrayList repositorio) {
        Filtro.repositorio = repositorio;
    }

    public static List<Evento> buscarPorTitulo(String Titulo) {
        List<Evento> eventosPorTitulo = new ArrayList<>();
        for(Evento evento : repositorio.getEventos()){
            if(evento.getCategoria().equalsIgnoreCase(Titulo)){
                eventosPorTitulo.add(evento);
            }
        }
        return eventosPorTitulo;
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
