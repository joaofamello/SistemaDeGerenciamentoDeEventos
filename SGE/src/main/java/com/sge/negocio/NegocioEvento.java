package com.sge.negocio;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.*;

import com.sge.negocio.excecao.*;

import com.sge.negocio.excecao.CategoriaNaoEncontradaException;
import com.sge.negocio.excecao.CidadeSemEventosException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


public class NegocioEvento {
    private final GerenciadorEventos gerenciador;
    private IRepositorioEventos repositorioEventos;
    private Filtro filtro;

    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
        this.filtro = new Filtro((RepositorioEventosArrayList) repositorioEventos);
        this.gerenciador = new GerenciadorEventos(repositorioEventos);
    }

    public ArrayList<Evento> listarTodosEventos() {
        return repositorioEventos.listar();
    }


    public void inserir(Evento evento) throws FormularioEventoInvalidoException {
        gerenciador.validarEvento(evento);
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

    public void alterarTitulo(Evento evento, String titulo) throws FormularioEventoInvalidoException {
        gerenciador.mudarTituloEvento(evento, titulo);
    }

    public void alterarDescricao(Evento evento, String descricao) throws FormularioEventoInvalidoException {
        gerenciador.mudarDescricaoEvento(evento, descricao);
    }

    public void alterarData(Evento evento, LocalDate novaData) throws FormularioEventoInvalidoException {
        gerenciador.mudarDataEvento(evento, novaData);
    }

    public void alterarEndereco(Evento evento, Endereco endereco) throws FormularioEventoInvalidoException {
        gerenciador.mudarEnderecoEvento(evento, endereco);
    }

    public void alterarCategoria(Evento evento, String novaCategoria) throws FormularioEventoInvalidoException {
        gerenciador.mudarCategoriaEvento(evento, novaCategoria);
    }

    public void alterarQtdeIngressos(Evento evento, int NovaQtdeIngressos) throws FormularioEventoInvalidoException {
        gerenciador.mudarQtdeIngressosEvento(evento, NovaQtdeIngressos);
    }

}