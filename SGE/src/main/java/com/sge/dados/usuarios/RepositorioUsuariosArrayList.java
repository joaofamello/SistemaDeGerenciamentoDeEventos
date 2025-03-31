package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;

public class RepositorioUsuariosArrayList implements IRepositorioUsuarios {

    private ArrayList<Usuario> usuarios;

    private RepositorioUsuariosArrayList() { usuarios = new ArrayList<Usuario>();}

    @Override
    public void inserir(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscar(int id) {
        Usuario usuario = null;
        for (Usuario usuario1 : usuarios) {
            if(usuario.getID() == id) {
                usuario = usuario1;
                break;
            }
        }
        return usuario;
    }

    @Override
    public ArrayList<Usuario> listar() {
        return null;
    }
}
