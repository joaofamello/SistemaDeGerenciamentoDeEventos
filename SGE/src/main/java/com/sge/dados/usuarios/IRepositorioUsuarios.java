package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IRepositorioUsuarios {

    public void inserir(Usuario usuario);

    public Usuario buscarUsuariosPorID(int id);

    public ArrayList<Usuario> listar();

    public Usuario buscarUsuariosPorNome(String nome);
}
