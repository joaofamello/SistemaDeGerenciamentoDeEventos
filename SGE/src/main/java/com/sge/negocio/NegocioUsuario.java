package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.EmailJaExistenteException;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.validacao.ValidarUsuario;


/**
 * Classe representa os negocios de um usuario.
 * Contém repositorioUsuarios, tamMinSenha.
 *
 * @author Jurandir e Guilherme Henrique.
 */

import java.util.ArrayList;
import java.util.List;

public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;
    private static final int tamMinSenha = 5;
    ValidarUsuario validarUsuario = new ValidarUsuario();

    /**
     * Construtor da classe NegocioUsuarios.
     *
     * @param repositorioUsuarios Repositorio de ususarios cadastrados.
     */
    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }


    public ArrayList<Usuario> listarTodosUsuarios() {
        return repositorioUsuarios.listar();
    }

    public void inserir(Usuario usuario) throws FormularioUsuarioInvalidoException{
        validarUsuario.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());
        repositorioUsuarios.inserir(usuario);
    }

    public void alterar(Usuario usuario) throws FormularioUsuarioInvalidoException{
        validarUsuario.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());
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
