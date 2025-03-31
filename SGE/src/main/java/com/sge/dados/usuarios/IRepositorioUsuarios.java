package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;

public interface IRepositorioUsuarios {

    public void inserir(Usuario usuario);

    public Usuario buscar(int id);

    public ArrayList<Usuario> listar();

}
