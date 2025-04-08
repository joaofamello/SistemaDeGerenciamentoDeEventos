package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.validacao.ValidarUsuario;
import java.util.ArrayList;


/**
 * Classe representa os negocios de um usuario.
 * Contém repositorioUsuarios, tamMinSenha.
 *
 * @author Jurandir e Guilherme Henrique.
 */
public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;
    ValidarUsuario validarUsuario = new ValidarUsuario();

    /**
     * Construtor da classe NegocioUsuarios.
     *
     * @param repositorioUsuarios Repositorio de ususarios cadastrados.
     */
    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    /**
     * Retorna uma lista com todos os usuários cadastrados no sistema.
     *
     * @return lista de usuários
     */
    public ArrayList<Usuario> listarTodosUsuarios() {
        return repositorioUsuarios.listar();
    }

    /**
     * Insere um novo usuário no sistema, após validação de seus dados.
     *
     * @param usuario objeto do usuário a ser inserido
     * @throws FormularioUsuarioInvalidoException caso algum campo obrigatório seja inválido ou nulo
     */
    public void inserir(Usuario usuario) throws FormularioUsuarioInvalidoException{
        boolean jaExiste = listarTodosUsuarios().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()) ||
                        u.getNomeUsuario().equalsIgnoreCase(usuario.getNomeUsuario()));

        if (jaExiste) return;

        validarUsuario.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());
        repositorioUsuarios.inserir(usuario);
    }

}
