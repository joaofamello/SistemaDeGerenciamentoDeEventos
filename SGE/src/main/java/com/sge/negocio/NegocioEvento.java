package com.sge.negocio;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Filtro;

import com.sge.negocio.entidade.GerenciadorEventos;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.*;

import com.sge.negocio.excecao.CategoriaNaoEncontradaException;
import com.sge.negocio.excecao.CidadeSemEventosException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe  representa os Negocios do evento.
 * Contém gerenciador, repositorioEventos e filtro.
 *
 * @author José Gustavo e Jurandir
 */
public class NegocioEvento {
    private final GerenciadorEventos gerenciador;
    private IRepositorioEventos repositorioEventos;
    private Filtro filtro;

    /**
     *Construtor da classe NegocioEvento.
     *
     * @param repositorioEventos repositorio com os eventos cadastrados.
     */
    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
        this.filtro = new Filtro((RepositorioEventosArrayList) repositorioEventos);
        this.gerenciador = new GerenciadorEventos(repositorioEventos);
    }


    public void inserir(Evento evento) throws FormularioEventoInvalidoException {
        gerenciador.validarEvento(evento);
        repositorioEventos.inserir(evento);
    }
    public void alterar(Evento evento) {
        repositorioEventos.alterar(evento);
    }

    public List<Evento> buscarPorTitulo(String Titulo) throws EventoNaoEncontradoException{
        List<Evento> eventosEncontrados = filtro.buscarPorTitulo(Titulo);
        if(eventosEncontrados.isEmpty()){
            throw new EventoNaoEncontradoException(Titulo);
        }

        return eventosEncontrados;
    }

    public List<Evento> buscarPorCidade(String cidade) throws CidadeSemEventosException {

        List<Evento> eventosPorCidade = filtro.buscarPorCidade(cidade);
        if(eventosPorCidade.isEmpty()){
            throw new CidadeSemEventosException(cidade);
        }
        return eventosPorCidade;
    }

    public List<Evento> buscarPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        List<Evento> eventos = filtro.buscarPorCategoria(categoria);
        if(eventos.isEmpty()){
            throw new CategoriaNaoEncontradaException(categoria);
        }
        return eventos;
    }

    /**
     *
     * @param evento Evento que será cancelado.
     * @param solicitante Usuario criador do evento.
     * @throws CancelamentoProibidoException  Se tentar cancelar um evento com menos de 48 horas para o dia da realização.
     * @throws PermissaoNegadaException Se o usuario não for o criador do evento.
     */
    public void cancelarEvento(Evento evento, Usuario solicitante) //Lembrar de passar o usuario que esta requisitando o metodo.
            throws CancelamentoProibidoException, PermissaoNegadaException {

        if (!evento.getAnfitriao().equals(solicitante)) {
            throw new PermissaoNegadaException("Permissão Negada!");
        }
        gerenciador.validarCancelamento(evento); // Valida as 48h
        gerenciador.cancelarEvento(evento);
    }

    public void mudarHoraEvento(Evento evento, LocalDateTime novoInicio, LocalDateTime novoFim, Usuario solicitante) throws PermissaoNegadaException, CancelamentoProibidoException {
        if(!evento.getAnfitriao().equals(solicitante)){
            throw new PermissaoNegadaException("Permissão Negada!");
        }
        gerenciador.mudarHoraEvento(evento, novoInicio, novoFim);

    }

}