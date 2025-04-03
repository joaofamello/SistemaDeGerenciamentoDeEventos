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
import java.time.temporal.ChronoUnit;
import java.util.List;


public class NegocioEvento {
    private IRepositorioEventos repositorioEventos;
    private Filtro filtro;


    private static final int limiteDeTempoParaCancelamento = 48;

    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
        this.filtro = new Filtro((RepositorioEventosArrayList) repositorioEventos);
    }


    public void inserir(Evento evento) throws FormularioEventoInvalidoException {
        validarEvento(evento);
        repositorioEventos.inserir(evento);
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

    private void validarEvento(Evento evento) throws FormularioEventoInvalidoException {
        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("titulo", "Título não pode ser vazio");
        }
        if (evento.getHoraInicio().isBefore(LocalDateTime.now())) {
            throw new FormularioEventoInvalidoException("data", "Data/hora deve ser futura");
        }
    }

    public void cancelarEvento(Evento evento, Usuario solicitante)
            throws CancelamentoProibidoException, PermissaoNegadaException {
        GerenciadorEventos gerenciadorEventos = new GerenciadorEventos(); //Precisa passar um anfitrião como parametro, consultar a logica e ver se faz sentido
        if (!evento.getAnfitriao().equals(solicitante)) {
            throw new PermissaoNegadaException("Cancelamento de evento");
        }
        validarCancelamento(evento);
        gerenciadorEventos.cancelarEvento(evento);
    }

    private void validarCancelamento(Evento evento) throws CancelamentoProibidoException {
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), evento.getHoraInicio());
        if (horasRestantes < limiteDeTempoParaCancelamento) {
            throw new CancelamentoProibidoException(evento.getHoraInicio());
        }
    }

}