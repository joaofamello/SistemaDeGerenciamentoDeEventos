package com.sge.negocio;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Filtro;
import com.sge.negocio.excecao.CategoriaNaoEncontradaException;
import com.sge.negocio.excecao.CidadeSemEventosException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;


public class NegocioEvento {
    private IRepositorioEventos repositorioEventos;
    private Filtro filtro;

    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
        this.filtro = new Filtro((RepositorioEventosArrayList) repositorioEventos);
    }
    private static final int limiteDeTempoParaCancelamento = 48;

    public void inserir(Evento evento) {
        repositorioEventos.inserir(evento);
    }
    public void alterar(Evento evento) {
        repositorioEventos.alterar(evento);
    }
    public void remover(Evento evento) {
        repositorioEventos.remover(evento);
    }

    public List<Evento> buscarPorTitulo(String Titulo) throws EventoNaoEncontradoException{
    List<Evento> eventosPorTitulo = filtro.buscarPorTitulo(Titulo);
    if(eventosPorTitulo.isEmpty()){
        throw new EventoNaoEncontradoException(Titulo);
    }
    return eventosPorTitulo;
    }

    public List<Evento> buscarPorCidade(String cidade) throws CidadeSemEventosException {
        List<Evento> eventosEncontrados = filtro.buscarPorCidade(cidade);
        if(eventosEncontrados.isEmpty()){
            throw new CidadeSemEventosException(cidade);
        }
        return eventosEncontrados;
    }

    public List<Evento> buscarPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        List<Evento> eventos = filtro.buscarPorCategoria(categoria);
        if(eventos.isEmpty()){
            throw new CategoriaNaoEncontradaException(categoria);
        }
        return eventos;
    }


}