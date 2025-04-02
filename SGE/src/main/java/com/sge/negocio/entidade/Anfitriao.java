package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Anfitriao extends Usuario {
    private ArrayList<Evento> eventosCriados;
    private final GerenciadorEntrada entrada = new GerenciadorEntrada();

    public Anfitriao(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha, ArrayList<Evento> eventosParticipando) {
        super(nomeCompleto, nomeUsuario, email, telefone, senha);
        this.eventosParticipando = eventosParticipando;
    }

    //Métodos Básicos de Gerenciamento dos Eventos
    public void listarEventos() {
        if (eventosCriados != null) {
            for (Evento evento : eventosCriados) {
                System.out.println(evento + "\n -------------------------------------");
            }
        } else {
            System.out.println("Nenhum evento cadastrado!"); // Criar um exception para isso
        }
    }

    public Evento buscarMeuEventoNome() {
        String nome = entrada.recebeString();
        for (Evento evento : eventosCriados) {
            if(nome.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null; // usar o exception de nenhum evento cadastrado
    }
    public Evento buscarMeuEventoID() {
        int ID = entrada.recebeInt();
        for (Evento evento : eventosCriados) {
            if(ID == evento.getID()){
                return evento;
            }
        }
        return null;
    }

    public void mudarTituloMeuEvento(Evento evento, String novoTitulo) {
        evento.setTitulo(novoTitulo);
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
        eventosCriados.remove(evento);
    }
}
