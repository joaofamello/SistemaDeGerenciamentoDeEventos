package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;


/**
 * Classe representa os negocios de um usuario.
 * Contém repositorioUsuarios, tamMinSenha.
 *
 * @author Jurandir e Guilherme Henrique.
 */

import java.util.ArrayList;

 main
public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;
    private static final int tamMinSenha = 5;

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

    public void inserir(Usuario usuario) throws FormularioUsuarioInvalidoException {
        validarUsuario(usuario);
        repositorioUsuarios.inserir(usuario);
    }

    public void alterar(Usuario usuario) throws FormularioUsuarioInvalidoException {
        validarUsuario(usuario);
        repositorioUsuarios.alterar(usuario);
    }

    public Usuario buscarUsuariosPorID(int ID) {
        return repositorioUsuarios.buscarUsuariosPorID(ID);
    }

    public Usuario buscarUsuariosPorNome(String nome) {
        Usuario usuario = repositorioUsuarios.buscarUsuariosPorNome(nome);
        return usuario;
    }

    /**
     *
     * @param usuario Usuario que vai ser analizado.
     * @throws FormularioUsuarioInvalidoException Se ocorre algum dado foi passado incorretamente.
     *
     * Esse metodo valida se as informações passadas na criação do usuario estão corretas.
     */
    private void validarUsuario(Usuario usuario) throws FormularioUsuarioInvalidoException {
        if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("nomeCompleto", "Nome completo obrigatório");
        }
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("nomeUsuario", "Nome de usuário obrigatório");
        }
        if (usuario.getSenha() == null || usuario.getSenha().length() < tamMinSenha) {
            throw new FormularioUsuarioInvalidoException("senha", "Senha deve ter no mínimo " + tamMinSenha + " caracteres");
        }
        if (usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("telefone", "Telefone obrigatório");
        }
        if(usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("e-mail", "e-mail obrigatório");
        }
    }
}
