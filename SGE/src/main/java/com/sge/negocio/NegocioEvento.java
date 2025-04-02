package com.sge.negocio;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.negocio.entidade.Evento;


public class NegocioEvento {
    private IRepositorioEventos repositorioEventos;

    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
    }

    public void inserir(Evento evento) {
        repositorioEventos.inserir(evento);
    }
    public void alterar(Evento evento) {
        repositorioEventos.alterar(evento);
    }
    public void remover(Evento evento) {
        repositorioEventos.remover(evento);
    }



}