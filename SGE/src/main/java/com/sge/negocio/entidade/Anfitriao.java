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

    // criar um método que dê acesso somente ao anfitrião à classe GerenciadorEventos

    public ArrayList<Evento> getEventosCriados() {
        return eventosCriados;
    }
}

