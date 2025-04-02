package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.entidade.Usuario;

public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;

    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void inserir(Usuario usuario) {
        repositorioUsuarios.inserir(usuario);
    }

    public Usuario buscarUsuariosPorID(int ID) {
        return repositorioUsuarios.buscarUsuariosPorID(ID);
    }

    public Usuario buscarUsuariosPorNome(String nome) {
        Usuario usuario = repositorioUsuarios.buscarUsuariosPorNome(nome);
        return usuario;
    }

}