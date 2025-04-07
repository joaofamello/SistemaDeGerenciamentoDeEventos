package com.sge.fachada;


import com.sge.dados.arquivos.GerenciadorDeDados;
import com.sge.dados.arquivos.PersistenciaDados;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.ingresso.Ingresso;
import com.sge.negocio.excecao.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SGE {
    private PersistenciaDados persistenciaDados;
    private GerenciadorDeDados gerenciadorDeDados;
    private static SGE instancia;
    private NegocioUsuario repositorioUsuario;
    private NegocioEvento repositorioEvento;

    private SGE() {
        persistenciaDados = new PersistenciaDados();
        gerenciadorDeDados = new GerenciadorDeDados();
        repositorioUsuario = new NegocioUsuario(new RepositorioUsuariosArrayList());
        repositorioEvento = new NegocioEvento(new RepositorioEventosArrayList());
    }

    public static SGE getInstancia() {
        if (instancia == null) {
            instancia = new SGE();
        }
        return instancia;
    }

    public void cadastrarUsuario(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha) throws FormularioUsuarioInvalidoException {
        Usuario usuario = new Usuario(nomeCompleto, nomeUsuario, email, telefone, senha);
        repositorioUsuario.inserir(usuario);
    }

    public Usuario LoginUsuario(String nomeUsuario, String senha) throws LoginFalhouException {
        List<Usuario> usuarios = repositorioUsuario.listarTodosUsuarios();

        for (Usuario usuarioExistente : usuarios) {
            if (usuarioExistente.getNomeUsuario().equalsIgnoreCase(nomeUsuario)) {
                if (usuarioExistente.getSenha().equals(senha)) {
                    return usuarioExistente;
                }
            }
        }

        throw new LoginFalhouException();
    }

    public List<Usuario> ListarUsuarios() {
        return repositorioUsuario.listarTodosUsuarios();
    }

    public List<Evento> ListarEventos() {
        return repositorioEvento.listarTodosEventos();
    }

    public void alterarUsuario(Usuario usuario) throws FormularioUsuarioInvalidoException, EmailJaExistenteException {
        repositorioUsuario.alterar(usuario);
    }

    public Usuario buscarUsuarioPorId(int id) throws UsuarioNaoEncontradoException {
        return repositorioUsuario.buscarUsuariosPorID(id);
    }

    public Usuario buscarUsuarioPorNome(String nome) throws UsuarioNaoEncontradoException {
        return repositorioUsuario.buscarUsuariosPorNome(nome);
    }

    public void cadastrarEvento(String Titulo, String Descricao, String Categoria, Endereco endereco, LocalDate data, LocalDateTime HoraInicio, LocalDateTime HoraFim, int qtdeIngressos, double valorBase, Usuario anfitriao) throws FormularioEventoInvalidoException, EventoDuplicadoException {
        Evento evento = new Evento(Titulo, Descricao, Categoria, endereco, data, HoraInicio, HoraFim, qtdeIngressos, valorBase, anfitriao);
        repositorioEvento.inserir(evento);
    }

    public void alterarEvento(Evento evento) throws FormularioEventoInvalidoException {
        //gerenciadorEntrada.AlterarEvento(evento, repositorioEvento);
    }

    public void cancelarEvento(Evento evento, Usuario usuario) throws PermissaoNegadaException, CancelamentoProibidoException {
        repositorioEvento.cancelarEvento(evento, usuario);
    }

    public List<Evento> buscarEventoPorTitulo(String titulo) throws EventoNaoEncontradoException {
        return repositorioEvento.buscarPorTitulo(titulo);
    }

    public List<Evento> buscarEventoPorCidade(String cidade) throws CidadeSemEventosException {
        return repositorioEvento.buscarPorCidade(cidade);
    }

    public List<Evento> buscarEventoPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        return repositorioEvento.buscarPorCategoria(categoria);
    }

    public void participarEvento(Usuario usuario, Evento evento) {

    }

    public void SalvarArquivoUsuario() {
        persistenciaDados.salvarUsuarios(repositorioUsuario.listarTodosUsuarios());
        SalvarArquivoParticipando();
    }

    public void SalvarArquivoParticipantes() {
        persistenciaDados.salvarParticipantes(repositorioEvento.listarTodosEventos());
    }

    public void SalvarArquivoParticipando() {
        persistenciaDados.salvarParticipado(repositorioUsuario.listarTodosUsuarios());
    }

    public void SalvarArquivoEvento() {
        persistenciaDados.salvarEventos(repositorioEvento.listarTodosEventos());
        SalvarArquivoParticipantes();
    }

    public void CarregarArquivos() {
        ArrayList<Usuario> usuarios = persistenciaDados.carregarUsuarios();
        ArrayList<Evento> eventos = persistenciaDados.carregarEventos(usuarios);
        persistenciaDados.carregarParticipantes(eventos, usuarios);
        persistenciaDados.carregarParticipado(usuarios, eventos);

        usuarios.forEach(usuario -> {
            try {
                repositorioUsuario.inserir(usuario);
            } catch (FormularioUsuarioInvalidoException e) {
                System.err.println("Erro ao carregar usuário " + usuario.getNomeUsuario() + ": " + e.getMessage());

            }
        });

        eventos.forEach(evento -> {
            try {
                repositorioEvento.inserir(evento);
            } catch (FormularioEventoInvalidoException | EventoDuplicadoException e) {
                System.err.println("Erro ao carregar evento " + evento.getTitulo() + ": " + e.getMessage());
            }
        });
    }

    public void existeSistemaUsers(String email, String nome) throws EmailJaExistenteException, UsernameJaExisteException {
        List<Usuario> usuarios = repositorioUsuario.listarTodosUsuarios();

        for (Usuario usuarioExistente : usuarios) {
            if (usuarioExistente.getEmail().equalsIgnoreCase(email)) {
                throw new EmailJaExistenteException();
            }
            if (usuarioExistente.getNomeUsuario().equalsIgnoreCase(nome)) {
                throw new UsernameJaExisteException();
            }
        }
    }

    public void validarConflitoPorEndereco(LocalDate dataEvento, LocalDateTime horaInicio, LocalDateTime horaFim, Endereco endereco) throws EventoDuplicadoException {
        for (Evento evento : repositorioEvento.listarTodosEventos()) {
            boolean mesmaData = evento.getData().isEqual(dataEvento);
            boolean mesmoEndereco = evento.getEndereco().equals(endereco);
            boolean conflitoHorario = !(horaInicio.isAfter(evento.getHoraFim().plusHours(1)) ||
                    horaFim.isBefore(evento.getHoraInicio().minusHours(1)));

            if (mesmaData && mesmoEndereco && conflitoHorario) {
                throw new EventoDuplicadoException("Já existe um evento no mesmo local e data: " + evento.getTitulo());
            }
        }
    }

    public List<Evento> buscarEventosPorEnderecoEData(Endereco endereco, LocalDate dataEvento, List<Evento> eventosExistentes) {
        List<Evento> filtrados = new ArrayList<>();
        for (Evento evento : eventosExistentes) {
            if (evento.getData().isEqual(dataEvento) && evento.getEndereco().equals(endereco)) {
                filtrados.add(evento);
            }
        }
        return filtrados;
    }

    public void comprarIngresso(Usuario usuarioLogado, Evento evento, Ingresso ingresso){
        usuarioLogado.adicionarIngresso(ingresso);
        usuarioLogado.participarDoEvento(evento);
        evento.participarDoEvento(usuarioLogado);
        ingresso.vender(usuarioLogado);
    }


}

