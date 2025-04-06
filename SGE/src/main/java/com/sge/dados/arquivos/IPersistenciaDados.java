package com.sge.dados.arquivos;

import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IPersistenciaDados {

    public void salvarUsuarios(List<Usuario> usuarios);

    public ArrayList<Usuario> carregarUsuarios();

    public void salvarEventos(ArrayList<Evento> eventos);

    public ArrayList<Evento> carregarEventos();


}
