package com.sge.negocio;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Filtro;

import java.util.ArrayList;
import java.util.List;


public class NegocioEvento {
    private IRepositorioEventos repositorioEventos;

    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
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

    public Evento buscarPorTitulo(String Titulo) throws EventoNaoEncontradoException{
    Filtro filtro = new Filtro();
    Evento evento = filtro.buscarPorTitulo(Titulo);
    if(evento == null){
        throw new EventoNaoEncontradoException();
    }
    return evento;
    }

    public List<Evento> buscarPorCidade(String cidade) throws CidadeSemEventosException{
        Filtro filtro = new Filtro();
        List<Evento> eventosEncontrados = filtro.buscarPorCidade(cidade);
        if(eventosEncontrados.isEmpty()){
            throw new CidadeSemEventosException();
        }
        return eventosEncontrados;
    }

    public List<Evento> buscarPorCategoria(String categoria) throws CategoriaNaoEncontradaException{
        Filtro filtro = new Filtro();
        List<Evento> eventos = filtro.buscarPorCidade(categoria);
        if(eventos.isEmpty()){
            throw new CategoriaNaoEncontradaException();
        }
        return eventos;
    }


}