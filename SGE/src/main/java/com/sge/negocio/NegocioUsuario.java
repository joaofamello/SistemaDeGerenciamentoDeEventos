package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.validacao.ValidarUsuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;

import java.util.ArrayList;

public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;
    private final ValidarUsuario validador;

    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.validador = new ValidarUsuario();
    }


    public ArrayList<Usuario> listarTodosUsuarios() {
        return repositorioUsuarios.listar();
    }

    public void inserir(Usuario usuario) throws FormularioUsuarioInvalidoException {
        validador.validar(usuario);
        repositorioUsuarios.inserir(usuario);
    }

    public void alterar(Usuario usuario) throws FormularioUsuarioInvalidoException {
        validador.validar(usuario);
        repositorioUsuarios.alterar(usuario);
    }

    public Usuario buscarUsuariosPorID(int ID) {
        return repositorioUsuarios.buscarUsuariosPorID(ID);
    }

    public Usuario buscarUsuariosPorNome(String nome) {
        Usuario usuario = repositorioUsuarios.buscarUsuariosPorNome(nome);
        return usuario;
    }

}
