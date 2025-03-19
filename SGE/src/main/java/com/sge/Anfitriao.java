package com.sge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Anfitriao {
    ArrayList<Evento> meusEventos;

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

    public void mudarDescricaoMeuEvento(Evento evento, String novaDescricao){
        evento.setDescricao(novaDescricao);
    }

    public void mudarDataMeuEvento(Evento evento, LocalDate novaData){
        evento.setData(novaData);
    }

    public void mudarHoraMeuEvento(Evento evento, LocalDateTime novoHora){
        evento.set
    }
}
