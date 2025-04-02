package com.sge.dados.eventos;

import com.sge.negocio.entidade.Evento;

import java.util.List;

public interface IRepositorioEventos {

    public void inserir(Evento evento);

    public void alterar(Evento evento);

    public void remover(Evento evento);

    public void salvarEventos(List<Evento> eventos);

    public List<Evento> carregarEventos();

}
