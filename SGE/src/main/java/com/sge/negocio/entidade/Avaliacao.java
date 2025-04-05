package com.sge.negocio.entidade;

import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.excecao.AvaliacaoInvalidaException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Avaliacao {
    private int nota;
    private String comentario;
    private Usuario avaliador;
    private Evento evento;
    private LocalDateTime dataAvaliacao;

    //Construtor para avaliacao
    public Avaliacao(Usuario avaliador, Evento evento, int nota, String comentario) throws AvaliacaoInvalidaException {
        if (nota < 1 || nota > 5) {
            throw new AvaliacaoInvalidaException("Nota deve ser entre 1 e 5");
        }
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new AvaliacaoInvalidaException("Comentário não pode ser vazio");
        }
        if (comentario.length() > 500) {
            throw new AvaliacaoInvalidaException("Comentário muito longo (máx. 500 caracteres)");
        }


        this.nota = nota;
        this.comentario = comentario.trim();
        this.avaliador = avaliador;
        this.evento = evento;
        this.dataAvaliacao = LocalDateTime.now();
    }

    //Getters e Setters
    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public Usuario getAvaliador() {
        return avaliador;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    @Override
    public String toString() {
        return String.format("[%d/5] %s - %s (%s)",
                nota,
                comentario,
                avaliador.getNomeUsuario(),
                dataAvaliacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}
