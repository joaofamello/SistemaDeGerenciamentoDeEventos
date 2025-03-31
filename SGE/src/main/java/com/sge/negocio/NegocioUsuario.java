package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.negocio.entidade.Usuario;

public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;

    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }
    
    public void inserir(Usuario usuario) {
        repositorioUsuarios.inserir(usuario);
    }




}
