package com.sge.negocio;

import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.entidade.*;

import com.sge.negocio.excecao.*;

import com.sge.negocio.excecao.CategoriaNaoEncontradaException;
import com.sge.negocio.excecao.CidadeSemEventosException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;
import com.sge.negocio.validacao.ValidarEvento;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe  representa os Negocios do evento.
 * Contém gerenciador, repositorioEventos e filtro.
 *
 * @author Guilherme, José Gustavo e Jurandir
 */
public class NegocioEvento {
    private final GerenciadorEventos gerenciador;
    private IRepositorioEventos repositorioEventos;
    private final ValidarEvento validador;
    private Filtro filtro;

    /**
     * Construtor da classe NegocioEvento.
     *
     * @param repositorioEventos repositorio com os eventos cadastrados.
     */
    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
        this.filtro = new Filtro((RepositorioEventosArrayList) repositorioEventos);
        this.gerenciador = new GerenciadorEventos(repositorioEventos);
        this.validador = new ValidarEvento();
    }

    /**
     * Lista todos os eventos cadastrados
     * @return lista com todos os eventos do repositório
     */
    public ArrayList<Evento> listarTodosEventos() {
        return repositorioEventos.listar();
    }


    public void inserir(Evento evento) throws FormularioEventoInvalidoException, EventoDuplicadoException {
        validador.validar( evento.getTitulo(),  evento.getDescricao(),
                 evento.getEndereco(),  evento.getData(), evento.getCategoria(), evento.getHoraInicio(), evento.getHoraFim(),
         evento.getQtdeIngressos(),  evento.getValorBase(), evento.getAnfitriao());
        repositorioEventos.inserir(evento);
    }

    public void inserirADM(Evento evento){
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

    /**
     *
     * @param evento Evento que será cancelado.
     * @param solicitante Usuario criador do evento.
     * @throws CancelamentoProibidoException  Se tentar cancelar um evento com menos de 48 horas para o dia da realização.
     * @throws PermissaoNegadaException Se o usuario não for o criador do evento.
     */
    public void cancelarEvento(Evento evento, Usuario solicitante)
            throws CancelamentoProibidoException, PermissaoNegadaException {

        if (!evento.getAnfitriao().equals(solicitante)) {
            throw new PermissaoNegadaException("Permissão Negada!");
        }
        gerenciador.validarCancelamento(evento); // Valida as 48h
        gerenciador.cancelarEvento(evento);
    }

}