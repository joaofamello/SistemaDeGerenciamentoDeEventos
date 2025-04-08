package com.sge.negocio;

import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.EmailJaExistenteException;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.validacao.ValidarUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representa os negocios de um usuario.
 * Contém repositorioUsuarios, tamMinSenha.
 *
 * @author Jurandir e Guilherme Henrique.
 */
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
        validarUsuario.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());
        repositorioUsuarios.inserir(usuario);
    }

    /**
     * Altera os dados de um usuário já existente no sistema.
     *
     * @param usuario objeto do usuário com dados atualizados
     * @throws FormularioUsuarioInvalidoException caso algum campo atualizado seja inválido
     */
    public void alterar(Usuario usuario) throws FormularioUsuarioInvalidoException{
        validarUsuario.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());
        repositorioUsuarios.alterar(usuario);
    }


    /**
     * Busca um usuário pelo seu ID.
     *
     * @param ID identificador único do usuário
     * @return o usuário correspondente ou null se não for encontrado
     */
    public Usuario buscarUsuariosPorID(int ID) {
        return repositorioUsuarios.buscarUsuariosPorID(ID);
    }

    /**
     * Busca um usuário pelo seu nome de usuário.
     *
     * @param nome nome de usuário
     * @return o usuário correspondente ou null se não for encontrado
     */
    public Usuario buscarUsuariosPorNome(String nome) {
        Usuario usuario = repositorioUsuarios.buscarUsuariosPorNome(nome);
        return usuario;
    }
}
