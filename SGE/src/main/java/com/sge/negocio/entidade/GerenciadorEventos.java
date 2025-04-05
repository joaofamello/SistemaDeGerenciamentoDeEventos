package com.sge.negocio.entidade;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.negocio.excecao.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representa um gerenciador de eventos.
 * Contém repositorio, limiteDeTempoParaCancelamento.
 *
 * @author João Francisco e Jurandir.
 */
public class GerenciadorEventos {
    private final IRepositorioEventos repositorio;
    private static final int limiteDeTempoParaCancelamento = 48;

    /**
     *Construtor da classe gerenciadorEventos.
     *
     * @param repositorio Repositorio com os eventos cadastrados.
     */
    public GerenciadorEventos(IRepositorioEventos repositorio) {
        this.repositorio = repositorio;
    }

    /**
     *
     * @param usuario Criador do evento.
     * @return Lista com todos os eventos cadastrados pelo anfitrião.
     * @throws NenhumEventoCriadoException Se ocorrer o anfitrião não possui eventos cadastrados.
     */
    //Metodo para listar os eventos de um anfitriao.
    public ArrayList<Evento> listarEventosCriados(Usuario usuario) throws NenhumEventoCriadoException {
       List<Evento> eventos = new ArrayList<>();
       eventos = Usuario.getEventosCriados();
       return (ArrayList<Evento>) eventos;
    }

    /**
     *
     * @param Titulo Titulo do evento.
     * @param usuario Usuario criador do evento.
     * @return Evento com o titulo informado.
     * @throws EventoNaoEncontradoException Ocorre se não encontrar evento com a infomação passada.
     * @throws NenhumEventoCriadoException Se ocorrer o anfitrião não possui eventos cadastrados.
     * @throws TituloVazioException Ocorre se o campo para titulo estiver vazio.
     */
    //Metodo para o anfitriao buscar um de seus eventos por nome.
    public Evento buscarEventoNome(String Titulo, Usuario usuario) throws EventoNaoEncontradoException, NenhumEventoCriadoException, TituloVazioException {
        if(Titulo == null || Titulo.trim().isEmpty()){
            throw new TituloVazioException();
        }
        if(Usuario.getEventosCriados() == null){
            throw new NenhumEventoCriadoException();
        }
        for(Evento evento : Usuario.getEventosCriados()){
            if(evento.getTitulo().equals(Titulo)){
                return evento;
            }
        }
        throw new EventoNaoEncontradoException(Titulo);
    }

    public void mudarTituloEvento(Evento evento, String novoTitulo) {
        evento.setTitulo(novoTitulo);
    }

    public void mudarDescricaoEvento(Evento evento, String novaDescricao) {
        evento.setDescricao(novaDescricao);
    }

    public void mudarDataEvento(Evento evento, LocalDate novaData){
        evento.setData(novaData);
    }

    public void mudarHoraEvento(Evento evento, LocalDateTime novoInicio, LocalDateTime novoFim) throws CancelamentoProibidoException {
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), evento.getHoraInicio());
        if(horasRestantes < limiteDeTempoParaCancelamento){
            throw new CancelamentoProibidoException(evento.getHoraInicio());
        }
        evento.setHoraInicio(novoInicio);
        evento.setHoraFim(novoFim);
    }

    public void mudarEnderecoEvento(Evento evento, Endereco endereco) {
        evento.setEndereco(endereco);
    }

    public void mudarCategoriaEvento(Evento evento, String categoria) {
        evento.setCategoria(categoria);
    }

    public void mudarQtdeIngressosEvento(Evento evento, int qtdeIngressos) {
        evento.setQtdeIngressos(qtdeIngressos);
    }

    /**
     *
     * @param evento Evento para ser editado.
     *
     * Esse metodo muda o estado do evento para representar se o evento esta ativo ou não.
     */
    public void cancelarEvento(Evento evento) {
        evento.setEstado(false); // Marca como inativo
        repositorio.alterar(evento);
    }

    /**
     *
     * @param evento Evento para ser analizado.
     * @throws CancelamentoProibidoException Ocorre se o evento estiver a menos de 48 horas para o dia da realização.
     *
     * Esse metodo verifica se falta pelo menos 48 horas ate o evento para ser possivel validar o cancelamento.
     */
    public void validarCancelamento(Evento evento) throws CancelamentoProibidoException {
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), evento.getHoraInicio());
        if (horasRestantes < limiteDeTempoParaCancelamento) {
            throw new CancelamentoProibidoException(evento.getHoraInicio());
        }
    }

    /**
     *
     * @param evento Evento para ser analizado.
     * @throws FormularioEventoInvalidoException Ocorre se algum campo na criação do evento for invalido.
     *
     * Esse metodo valida se o evento recebeu todos os atributos corretamento.
     */
    public void validarEvento(Evento evento) throws FormularioEventoInvalidoException {
        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("titulo", "Título não pode ser vazio");
        }
        if (evento.getHoraInicio().isBefore(LocalDateTime.now())) {
            throw new FormularioEventoInvalidoException("data", "Data/hora deve ser futura");
        }
    }


}
