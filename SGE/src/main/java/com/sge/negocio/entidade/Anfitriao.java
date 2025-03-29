package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Anfitriao extends Usuario {
    ArrayList<Evento> meusEventos;
    ArrayList<Evento> eventosParticipando;

    public Anfitriao(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha, ArrayList<Evento> meusEventos, ArrayList<Evento> eventosParticipando) {
        super(nomeCompleto, nomeUsuario, email, telefone, senha);
        this.meusEventos = meusEventos;
        this.eventosParticipando = eventosParticipando;
    }

    //Métodos de Gerenciamento dos Eventos
    public void listarEventos() {
        if (meusEventos != null) {
            for (Evento evento : meusEventos) {
                System.out.println(evento + "\n -------------------------------------");
            }
        } else {
            System.out.println("Nenhum evento cadastrado!"); // Criar um exception pra isso
        }
    }

    public Evento buscarMeuEventoNome(String nome) {
        for (Evento evento : meusEventos) {
            if(nome.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null; // usar o exception de nenhum evento cadastrado
    }
    public Evento buscarMeuEventoID(int ID) {
        for (Evento evento : meusEventos) {
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

    public void mudarCategoriaMeuEvento(Evento evento, Categoria categoria) {
        evento.setCategoria(categoria);
    }

    public void mudarQtdeIngressosMeuEvento(Evento evento, int qtdeIngressos) {
        evento.setQtdeIngressos(qtdeIngressos);
    }

    public void excluirMeuEvento(Evento evento) { // pensar melhor nisso aqui
        meusEventos.remove(evento);
    }

    public void editarMeuEvento(Evento evento) { // nisso aqui também

    }
}
