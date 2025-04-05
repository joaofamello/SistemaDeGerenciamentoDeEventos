package com.sge.fachada;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.*;

import java.util.List;

public class SGE {

    private static SGE instancia;
    private NegocioUsuario negocioUsuario;
    private NegocioEvento negocioEvento;

    private SGE(){
        RepositorioUsuariosArrayList repositorio = new RepositorioUsuariosArrayList();
        this.negocioUsuario = new NegocioUsuario(repositorio);
        this.negocioEvento = new NegocioEvento((IRepositorioEventos) repositorio);
    }

    public static SGE getInstancia(){
        if(instancia == null){
            instancia = new SGE();
        }
        return instancia;
    }
    public void cadastrarUsuario(Usuario usuario) throws FormularioUsuarioInvalidoException {
        negocioUsuario.inserir(usuario);
    }

    public void alterarUsuario(Usuario usuario) throws FormularioUsuarioInvalidoException {
        negocioUsuario.alterar(usuario);
    }

    public Usuario buscarUsuarioPorId(int id) throws UsuarioNaoEncontradoException {
        return negocioUsuario.buscarUsuariosPorID(id);
    }

    public Usuario buscarUsuarioPorNome(String nome) throws UsuarioNaoEncontradoException {
        return negocioUsuario.buscarUsuariosPorNome(nome);
    }

    public void cadastrarEvento(Evento evento) throws FormularioEventoInvalidoException {
        negocioEvento.inserir(evento);
    }

    public void alterarEvento(Evento evento) throws FormularioEventoInvalidoException {
        negocioEvento.alterar(evento);
    }

    public void cancelarEvento(Evento evento, Usuario usuario) throws PermissaoNegadaException, CancelamentoProibidoException {
        negocioEvento.cancelarEvento(evento, usuario);
    }

    public List<Evento> buscarEventoPortitulo(String titulo) throws EventoNaoEncontradoException {
        return negocioEvento.buscarPorTitulo(titulo);
    }

    public List<Evento> buscarEventoPorCidade(String cidade) throws CidadeSemEventosException {
        return negocioEvento.buscarPorCidade(cidade);
    }

    public List<Evento> buscarEventoPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        return negocioEvento.buscarPorCategoria(categoria);
    }
}
