package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface IRepositorioUsuarios {

    public void inserir(Usuario usuario);

    public Usuario buscar(int id);

    public ArrayList<Usuario> listar();

    public void salvarUsuarios(List<Usuario> usuarios){
        List<Usuario> carregarUsuarios();
    }


}
