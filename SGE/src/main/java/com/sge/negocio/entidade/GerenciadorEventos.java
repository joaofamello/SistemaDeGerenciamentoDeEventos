package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GerenciadorEventos {
    private final Anfitriao operador;
    private final GerenciadorEntrada entrada = new GerenciadorEntrada();

    public GerenciadorEventos(Anfitriao operador) {
        this.operador = operador;
    }

    //Métodos Básicos de Gerenciamento dos Eventos
    public String listarEventos() {
        if (operador.getEventosCriados() != null) {
            for (Evento evento : operador.getEventosCriados()) {
                return evento.eventoFormatado();
            }
        } else {
            System.out.println("Nenhum evento cadastrado!"); // Criar um exception para isso
        }
        return null;
    }

    public String buscarEventoNome() {
        String nome = entrada.recebeString();
        for (Evento evento : operador.getEventosCriados()) {
            if(nome.equals(evento.getTitulo())) {
                return evento.eventoFormatado();
            }
        }
        return null; // usar o exception de nenhum evento cadastrado
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

    public void mudarHoraEvento(Evento evento, LocalDateTime novoInicio, LocalDateTime novoFim) {
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

    public void cancelarEvento(Evento evento) { // pensar melhor nisso aqui
        evento.setEstado(false);
    }

}
