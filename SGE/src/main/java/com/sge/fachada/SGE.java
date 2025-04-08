package com.sge.fachada;

import com.sge.dados.arquivos.GerenciadorDeDados;
import com.sge.dados.arquivos.PersistenciaDados;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.GerenciadorEventos;
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

/**
 * Responsável por orquestrar as operações de alto nível envolvendo usuários, eventos,
 * persistência de dados e regras de negócio.
 * @author Jose Gustavo e Guilherme
 */
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

    /**
     * Obtém a instância única da fachada (Singleton).
     * @return instância única do SGE
     */
    public static SGE getInstancia() {
        if (instancia == null) {
            instancia = new SGE();
        }
        return instancia;
    }

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param nomeCompleto nome completo do usuário
     * @param nomeUsuario nome de usuário
     * @param email email do usuário
     * @param telefone telefone do usuário
     * @param senha senha do usuário
     * @throws FormularioUsuarioInvalidoException se algum campo estiver inválido
     */
    public void cadastrarUsuario(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha) throws FormularioUsuarioInvalidoException {
        Usuario usuario = new Usuario(nomeCompleto, nomeUsuario, email, telefone, senha);
        repositorioUsuario.inserir(usuario);
    }

    /**
     * Realiza o login de um usuário.
     *
     * @param nomeUsuario nome de usuário
     * @param senha senha
     * @return o usuário autenticado
     * @throws LoginFalhouException se o login falhar
     */
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

    /**
     * Lista todos os eventos cadastrados.
     * @return lista de eventos
     */
    public List<Evento> ListarEventos() {
        return repositorioEvento.listarTodosEventos();
    }


    /**
     * Cadastra um novo evento.
     *
     * @param Titulo título do evento
     * @param Descricao descrição do evento
     * @param Categoria categoria do evento
     * @param endereco endereço onde o evento ocorrerá
     * @param data data do evento
     * @param HoraInicio hora de início
     * @param HoraFim hora de término
     * @param qtdeIngressos quantidade de ingressos
     * @param valorBase valor base dos ingressos
     * @param anfitriao usuário anfitrião do evento
     * @throws FormularioEventoInvalidoException se os dados forem inválidos
     * @throws EventoDuplicadoException se o evento for duplicado
     */
    public void cadastrarEvento(String Titulo, String Descricao, String Categoria, Endereco endereco, LocalDate data, LocalDateTime HoraInicio, LocalDateTime HoraFim, int qtdeIngressos, double valorBase, Usuario anfitriao) throws FormularioEventoInvalidoException, EventoDuplicadoException {
        Evento evento = new Evento(Titulo, Descricao, Categoria, endereco, data, HoraInicio, HoraFim, qtdeIngressos, valorBase, anfitriao);
        repositorioEvento.inserir(evento);
    }


    /**
     * Cancela um evento, desde que as regras de permissão e prazo permitam.
     *
     * @param evento evento a ser cancelado
     * @param usuario usuário solicitante
     * @throws PermissaoNegadaException se o usuário não for o anfitrião
     * @throws CancelamentoProibidoException se faltarem menos de 48h para o evento
     */
    public void cancelarEvento(Evento evento, Usuario usuario) throws PermissaoNegadaException, CancelamentoProibidoException {
        repositorioEvento.cancelarEvento(evento, usuario);
    }

    /**
     * Cancela a inscrição do usuário em um evento.
     *
     * @param usuarioLogado usuário que deseja cancelar
     * @param eventoSelecionado evento do qual deseja sair
     * @throws ErroCancelarInscricaoException se o usuário não estiver inscrito
     */
    public void cancelarInscricao(Usuario usuarioLogado, Evento eventoSelecionado) throws ErroCancelarInscricaoException {
        if (usuarioLogado.getEventosParticipados().contains(eventoSelecionado)) {
            usuarioLogado.getEventosParticipados().remove(eventoSelecionado);
            eventoSelecionado.getParticipantes().remove(usuarioLogado);
        } else {
            throw new ErroCancelarInscricaoException("Usuário não está participando deste evento.");
        }
    }

    /**
     * Busca eventos pelo título.
     *
     * @param titulo título do evento
     * @return lista de eventos encontrados
     * @throws EventoNaoEncontradoException se nenhum for encontrado
     */
    public List<Evento> buscarEventoPorTitulo(String titulo) throws EventoNaoEncontradoException {
        return repositorioEvento.buscarPorTitulo(titulo);
    }

    /**
     * Busca eventos pela cidade do endereço.
     *
     * @param cidade nome da cidade
     * @return lista de eventos na cidade
     * @throws CidadeSemEventosException se nenhum evento estiver registrado nessa cidade
     */
    public List<Evento> buscarEventoPorCidade(String cidade) throws CidadeSemEventosException {
        return repositorioEvento.buscarPorCidade(cidade);
    }

    /**
     * Busca eventos por categoria.
     *
     * @param categoria categoria do evento
     * @return lista de eventos da categoria
     * @throws CategoriaNaoEncontradaException se nenhum evento for encontrado na categoria
     */
    public List<Evento> buscarEventoPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        return repositorioEvento.buscarPorCategoria(categoria);
    }

    /**
     * Salva todos os dados de usuários e suas participações no arquivo.
     */
    public void SalvarArquivoUsuario() {
        persistenciaDados.salvarUsuarios(repositorioUsuario.listarTodosUsuarios());
        SalvarArquivoParticipando();
    }

    /**
     * Salva os participantes de cada evento no arquivo.
     */
    public void SalvarArquivoParticipantes() {
        persistenciaDados.salvarParticipantes(repositorioEvento.listarTodosEventos());
    }

    /**
     * Salva os eventos que cada usuário participa.
     */
    public void SalvarArquivoParticipando() {
        persistenciaDados.salvarParticipado(repositorioUsuario.listarTodosUsuarios());
    }

    /**
     * Salva os dados dos eventos e suas participações.
     */
    public void SalvarArquivoEvento() {
        persistenciaDados.salvarEventos(repositorioEvento.listarTodosEventos());
        SalvarArquivoParticipantes();
    }


    /**
     * Carrega todos os arquivos do sistema: usuários, eventos e participações.
     */
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


    /**
     * Verifica se já existe usuário com o mesmo email ou nome de usuário.
     *
     * @param email email a verificar
     * @param nome nome de usuário a verificar
     * @throws EmailJaExistenteException se o email já estiver em uso
     * @throws UsernameJaExisteException se o nome de usuário já estiver em uso
     */
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


    /**
     * Valida se já existe um evento no mesmo local e horário (mínimo 1h de intervalo).
     *
     * @param dataEvento data do evento
     * @param horaInicio horário de início
     * @param horaFim horário de fim
     * @param endereco endereço do evento
     * @throws EventoDuplicadoException se houver conflito de local/data/horário
     */
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

    /**
     * Registra a compra de ingresso e atualiza todas as associações.
     *
     * @param usuarioLogado comprador
     * @param evento evento do ingresso
     * @param ingresso ingresso adquirido
     */
    public void comprarIngresso(Usuario usuarioLogado, Evento evento, Ingresso ingresso)  throws IngressosEsgotadoException{
        if(evento.getIngressosDisponiveis() == 0){
            throw new IngressosEsgotadoException(evento.getTitulo());
        }
        usuarioLogado.adicionarIngresso(ingresso);
        usuarioLogado.participarDoEvento(evento);
        GerenciadorEventos.participarDoEvento(usuarioLogado, evento);
        ingresso.vender(usuarioLogado);
    }

    /**
     * Lista todos os eventos que o usuário está participando.
     *
     * @param usuario usuário logado
     * @return lista de eventos onde ele participa
     */
    public List<Evento> listarEventosParticipantes(Usuario usuario) {
        List<Evento> eventosDoUsuario = new ArrayList<>();

        for (Evento evento : repositorioEvento.listarTodosEventos()) {
            if (evento.getParticipantes().contains(usuario)) {
                eventosDoUsuario.add(evento);
            }
        }

        return eventosDoUsuario;
    }

    public List<Usuario> listarUsuarios(){
        return repositorioUsuario.listarTodosUsuarios();
    }


}

