package com.sge.dados.arquivos;

import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;

public interface IPersistenciaDados {

    public void salvarUsuarios(ArrayList<Usuario> usuarios);

    public ArrayList<Usuario> carregarUsuarios();

    public void salvarEventos(ArrayList<Evento> eventos);

    public ArrayList<Evento> carregarEventos();


}
