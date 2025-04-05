package com.sge.dados.eventos;

import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IRepositorioEventos {

    public void inserir(Evento evento);

    public void alterar(Evento evento);

    public void remover(Evento evento);

    public ArrayList<Evento> listar();

    public List<Evento> getEventos();
}
