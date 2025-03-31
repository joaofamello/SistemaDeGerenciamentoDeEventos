package com.sge.dados.eventos;

import com.sge.negocio.entidade.Evento;

public interface IRepositorioEventos {

    public void inserir(Evento evento);

    public void alterar(Evento evento);

    public void remover(Evento evento);

}
