package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;

public class RepositorioUsuariosArrayList implements IRepositorioUsuarios {

    private ArrayList<Usuario> usuarios;

    public RepositorioUsuariosArrayList() { usuarios = new ArrayList<Usuario>();}

    @Override
    public void inserir(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarUsuariosPorID(int id) {
        Usuario usuarioProcurado = null;
        for (Usuario usuario : usuarios) {
            if(usuario.getID() == id) {
                usuarioProcurado = usuario;
                break;
            }
        }
        return usuarioProcurado;
    }

    @Override
    public ArrayList<Usuario> listar() {
        return null;
    }

}
