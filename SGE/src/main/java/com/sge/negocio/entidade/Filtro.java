package com.sge.negocio.entidade;
import com.sge.dados.eventos.RepositorioEventosArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um filtro onde acontece a busca por eventos.
 * Contém repositorio.
 *
 * @author José Gustavo
 */
public class Filtro {
    private static RepositorioEventosArrayList repositorio;

    /**
     * Construtor da classe filtro.
     *
     * @param repositorio Repositorio com os eventos.
     */
    public Filtro(RepositorioEventosArrayList repositorio) {
        Filtro.repositorio = repositorio;
    }

    /**
     *
     * @param Titulo Titulo do evento.
     * @return Lista de eventos com o titulo recebido.
     */
    public static List<Evento> buscarPorTitulo(String Titulo) {
        List<Evento> eventosPorTitulo = new ArrayList<>();
        for(Evento evento : repositorio.getEventos()){
            if(evento.getTitulo().equalsIgnoreCase(Titulo)){
                eventosPorTitulo.add(evento);
            }
        }
        return eventosPorTitulo;
    }

    /**
     *
     * @param categoria Categoria do evento.
     * @return Lista os eventos que possuem a mesma categoria que a buscada.
     */
    public static List<Evento> buscarPorCategoria(String categoria) {
        List<Evento> eventos = new ArrayList<>();
        for(Evento evento : repositorio.getEventos()){
            if(evento.getCategoria().equalsIgnoreCase(categoria)){
                eventos.add(evento);
            }
        }
        return eventos;
    }

    /**
     *
     * @param cidade Cidade do evento.
     * @return Lista os eventos cuja cidade seja a mesma da buscada.
     */
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
