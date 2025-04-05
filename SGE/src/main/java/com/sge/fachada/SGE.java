package com.sge.fachada;

//import java.util.Scanner;
import com.sge.negocio.entidade.GerenciadorEventos;
import com.sge.dados.arquivos.GerenciadorDeDados;
import com.sge.dados.arquivos.PersistenciaDados;
import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.negocio.entidade.GerenciadorEntrada;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.*;
import java.util.List;

public class SGE {
    private PersistenciaDados persistenciaDados;
    private GerenciadorEntrada gerenciadorEntrada;
    //private final Scanner sc = new Scanner(System.in);
    private GerenciadorDeDados gerenciadorDeDados;
    private static SGE instancia;
    private NegocioUsuario repositorioUsuario;
    private NegocioEvento repositorioEvento;

    private SGE(){
        persistenciaDados = new PersistenciaDados();
        gerenciadorEntrada = new GerenciadorEntrada();
        gerenciadorDeDados = new GerenciadorDeDados();
        repositorioUsuario = new NegocioUsuario(new RepositorioUsuariosArrayList());
        repositorioEvento = new NegocioEvento(new RepositorioEventosArrayList());
    }

    public static SGE getInstancia(){
        if(instancia == null){
            instancia = new SGE();
        }
        return instancia;
    }
    public void cadastrarUsuario() throws FormularioUsuarioInvalidoException {
        gerenciadorEntrada.cadastrarUsuario(repositorioUsuario);
    }

    public void alterarUsuario(Usuario usuario) throws FormularioUsuarioInvalidoException {
        repositorioUsuario.alterar(usuario);
    }

    public Usuario buscarUsuarioPorId(int id) throws UsuarioNaoEncontradoException {
        return repositorioUsuario.buscarUsuariosPorID(id);
    }

    public Usuario buscarUsuarioPorNome(String nome) throws UsuarioNaoEncontradoException {
        return repositorioUsuario.buscarUsuariosPorNome(nome);
    }

    public void cadastrarEvento(Usuario usuario) throws FormularioEventoInvalidoException {
        gerenciadorEntrada.criarEvento(usuario, repositorioEvento);
    }

    public void alterarEvento(Evento evento) throws FormularioEventoInvalidoException {
        gerenciadorEntrada.AlterarEvento(evento, repositorioEvento);
    }

    public void cancelarEvento(Evento evento, Usuario usuario) throws PermissaoNegadaException, CancelamentoProibidoException {
        repositorioEvento.cancelarEvento(evento, usuario);
    }

    public List<Evento> buscarEventoPortitulo(String titulo) throws EventoNaoEncontradoException {
        return repositorioEvento.buscarPorTitulo(titulo);
    }

    public List<Evento> buscarEventoPorCidade(String cidade) throws CidadeSemEventosException {
        return repositorioEvento.buscarPorCidade(cidade);
    }

    public List<Evento> buscarEventoPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        return repositorioEvento.buscarPorCategoria(categoria);
    }

    public void SalvarArquivos(){
        persistenciaDados.salvarUsuarios(repositorioUsuario.listarTodosUsuarios());
        persistenciaDados.salvarEventos(repositorioEvento.listarTodosEventos());
    }

    public void CarregarArquivos(){
        persistenciaDados.carregarUsuarios();
        persistenciaDados.carregarEventos();
    }

}
