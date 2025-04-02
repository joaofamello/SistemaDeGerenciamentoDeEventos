package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GerenciadorEventos {
    private final Anfitriao operador;
    private GerenciadorEntrada entrada = new GerenciadorEntrada();

    public GerenciadorEventos(Anfitriao operador) {
        this.operador = operador;
    }

    //Métodos Básicos de Gerenciamento dos Eventos
    public String listarEventos() {
        if(operador.getEventosCriados() != null){
            for(Evento evento : operador.getEventosCriados()){
                return evento.eventoFormatado();
            }
        }
        return null;
    }

    public String buscarMeuEventoNome() {
        String nome = entrada.recebeString();
        for (Evento evento : operador.getEventosCriados()) {
            if(nome.equals(evento.getTitulo())) {
                return evento.eventoFormatado();
            }
        }
        return null; // usar o exception de nenhum evento cadastrado
    }

    public String buscarMeuEventoCategoria() {
        String categoria = entrada.recebeString();
        for (Evento evento : operador.getEventosCriados()) {
            if(categoria.equals(evento.getCategoria())) {
                return evento.eventoFormatado();
            }
        }
        return null;
    }

    public String mudarTituloMeuEvento(Evento evento, String novoTitulo) {
        evento.setTitulo(novoTitulo);
        System.out.println("Evento editado!");
        return evento.eventoFormatado();
    }

    public void mudarDescricaoMeuEvento(Evento evento, String novaDescricao) {
        evento.setDescricao(novaDescricao);
    }

    public void mudarDataMeuEvento(Evento evento, LocalDate novaData){
        evento.setData(novaData);
    }

    public void mudarHoraMeuEvento(Evento evento, LocalDateTime novoInicio, LocalDateTime novoFim) {
        evento.setHoraInicio(novoInicio);
        evento.setHoraFim(novoFim);
    }

    public void mudarEnderecoMeuEvento(Evento evento, Endereco endereco) {
        evento.setEndereco(endereco);
    }

    public void mudarCategoriaMeuEvento(Evento evento, String categoria) {
        evento.setCategoria(categoria);
    }

    public void mudarQtdeIngressosMeuEvento(Evento evento, int qtdeIngressos) {
        evento.setQtdeIngressos(qtdeIngressos);
    }

    public void excluirMeuEvento(Evento evento) { // pensar melhor nisso aqui
        operador.getEventosCriados().remove(evento);
    }

}
