package com.sge.negocio.entidade;

import com.sge.negocio.excecao.NenhumEventoCriadoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Anfitriao extends Usuario {
    private static ArrayList<Evento> eventosCriados;
    private final GerenciadorEntrada entrada = new GerenciadorEntrada();

    public Anfitriao(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha, ArrayList<Evento> eventosParticipando) {
        super(nomeCompleto, nomeUsuario, email, telefone, senha);
        this.eventosParticipando = eventosParticipando;
    }

    // criar um metodo que de acesso somente ao anfitriao a classe GerenciadorEventos

    public static List<Evento> getEventosCriados() throws NenhumEventoCriadoException {
        if (eventosCriados == null){
            throw new NenhumEventoCriadoException();
        }
        return eventosCriados;
    }
}

