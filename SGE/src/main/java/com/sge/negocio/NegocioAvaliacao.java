package com.sge.negocio;

import com.sge.dados.avaliacao.IRepositorioParticipacao;
import com.sge.dados.avaliacao.RepositorioParticipacao;
import com.sge.negocio.entidade.Avaliacao;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.AvaliacaoInvalidaException;
import com.sge.negocio.validacao.ValidarAvaliacao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class NegocioAvaliacao {
    private final IRepositorioParticipacao repositorio;
    private final ValidarAvaliacao validador;

    public NegocioAvaliacao() {
        this.repositorio = new RepositorioParticipacao();
        this.validador = new ValidarAvaliacao(repositorio);
    }

    public Avaliacao registrarAvaliacao(Usuario usuario, Evento evento, int nota, String comentario)
            throws AvaliacaoInvalidaException {

        // Validação prévia
        validador.validar(usuario, evento);

        // Cria e registra a avaliação
        Avaliacao avaliacao = new Avaliacao(usuario, evento, nota, comentario);
        repositorio.registrarAvaliacao(usuario, evento, nota, comentario);

        return avaliacao;
    }

    //Metodo para calcular a media das avaliacoes de um evento
    public double calcularMediaAvaliacoes(Evento evento) {
        try {
            return Files.lines(Paths.get("eventos_participantes.txt"))
                    .filter(linha -> {
                        String[] dados = linha.split(";");
                        return dados[1].equals(String.valueOf(evento.getID())) &&
                                !dados[2].isEmpty();
                    })
                    .mapToInt(linha -> Integer.parseInt(linha.split(";")[2]))
                    .average()
                    .orElse(0.0);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao calcular média", e);
        }
    }

    //Lista todas as avaliações de um evento
    public List<String> listarAvaliacoes(Evento evento) {
        try {
            return Files.lines(Paths.get("eventos_participantes.txt"))
                    .filter(linha -> {
                        String[] dados = linha.split(";");
                        return dados[1].equals(String.valueOf(evento.getID())) &&
                                !dados[2].isEmpty();
                    })
                    .map(linha -> {
                        String[] dados = linha.split(";");
                        return String.format("Nota: %s - Comentário: %s",
                                dados[2], dados[3]);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao listar avaliações", e);
        }
    }
}
