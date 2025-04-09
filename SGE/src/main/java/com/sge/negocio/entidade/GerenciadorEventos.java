package com.sge.negocio.entidade;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.negocio.excecao.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
     * Construtor da classe gerenciadorEventos.
     *
     * @param repositorio Repositorio com os eventos cadastrados.
     */
    public GerenciadorEventos(IRepositorioEventos repositorio) {
        this.repositorio = repositorio;
    }

    /**
     *
     * @param evento Evento que será editado.
     *
     * Muda o estado do evento para inativo e altera, também, seu estado no repositório.
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
     * Verifica se falta pelo menos 48 horas até o evento para ser possivel validar o cancelamento.
     */
    public void validarCancelamento(Evento evento) throws CancelamentoProibidoException {
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), evento.getHoraInicio());
        if (horasRestantes < limiteDeTempoParaCancelamento) {
            throw new CancelamentoProibidoException(evento.getHoraInicio());
        }
    }

    /**
     * Usado pelo repositório para adicionar os participantes de volta ao repositório sem fazer verificações.
     *
     * @param usuario usuário que será adicionado ao repositório de participantes do evento
     */
    public static void participarDoEventoADM(Usuario usuario, Evento evento) throws NenhumEventoCriadoException {
        evento.setParticipantes(usuario);
        evento.incrementaIngressosVendidos();
        usuario.participarDoEvento(evento);
    }

    //public static void

    /**
     * Realiza uma verificação do estado do evento e insere o usuário no repositório de participantes.
     * @param usuario Usuário logado quem irá interagir com o evento
     */
    public static void participarDoEvento(Usuario usuario, Evento evento) {
        if (!evento.getEstado()) {
            throw new IllegalStateException("Evento inativo. Participação cancelada.");
        }

        if (evento.getIngressosDisponiveis() <= 0) {
            throw new IllegalStateException("Evento lotado!");
        }

        evento.setParticipantes(usuario);
        evento.incrementaIngressosVendidos();
        usuario.participarDoEvento(evento);

    }

    public static String RetornarEstado(Evento evento) {
        boolean estado = evento.getEstado();
        int ingressosDisponiveis = evento.getIngressosDisponiveis();
        LocalDateTime agora = LocalDateTime.now();
        // Se o evento já passou (início antes de agora)
        if (evento.getHoraFim().isBefore(agora)) {
            return "Encerrado";
        }

        // Se o evento foi manualmente cancelado
        if (!estado) {
            return "Inativo";
        }

        // Se não há mais ingressos disponíveis
        if (ingressosDisponiveis == 0) {
            return "Lotado";
        }


        return "Ativo";
    }


}
