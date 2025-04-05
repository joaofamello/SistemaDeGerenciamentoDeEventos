package com.sge.dados.avaliacao;

import com.sge.dados.arquivos.GerenciadorDeDados;
import com.sge.negocio.entidade.Avaliacao;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class RepositorioParticipacao implements IRepositorioParticipacao {
    private static final Path arquivo = Paths.get("AvaliacoesData.txt");

    @Override
    public void registrarParticipacao(Usuario usuario, Evento evento) {
        String linha = String.format("%d;%d;;;\n", usuario.getID(), evento.getID());
        try {
            Files.write(arquivo, linha.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao registrar participação", e);
        }
    }

    @Override
    public void registrarAvaliacao(Usuario usuario, Evento evento, int nota, String comentario) {
        // Primeiro marca como participante (se não estiver)
        if (!isParticipante(usuario, evento)) {
            registrarParticipacao(usuario, evento);
        }

        // Atualiza a linha com avaliação
        try {
            List<String> linhas = Files.readAllLines(arquivo);

            for (int i = 0; i < linhas.size(); i++) {
                String[] dados = linhas.get(i).split(";");
                if (dados[0].equals(String.valueOf(usuario.getID())) &&
                        dados[1].equals(String.valueOf(evento.getID()))) {

                    linhas.set(i, String.format("%s;%s;%d;%s;%s",
                            dados[0], dados[1], nota, comentario, LocalDateTime.now()));
                    break;
                }
            }

            Files.write(arquivo, linhas);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao registrar avaliação", e);
        }
    }

    @Override
    public boolean isParticipante(Usuario usuario, Evento evento) {
        try {
            if (!Files.exists(arquivo)) return false;

            return Files.lines(arquivo)
                    .anyMatch(linha -> {
                        String[] dados = linha.split(";");
                        return dados[0].equals(String.valueOf(usuario.getID())) &&
                                dados[1].equals(String.valueOf(evento.getID()));
                    });
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar participação", e);
        }
    }

    @Override
    public boolean jaAvaliou(Usuario usuario, Evento evento) {
        try {
            if (!Files.exists(arquivo)) return false;

            return Files.lines(arquivo)
                    .anyMatch(linha -> {
                        String[] dados = linha.split(";");
                        return dados[0].equals(String.valueOf(usuario.getID())) &&
                                dados[1].equals(String.valueOf(evento.getID())) &&
                                !dados[2].isEmpty(); // Tem nota => avaliou
                    });
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar avaliação", e);
        }
    }
}
