package com.sge.dados;

import java.io.*;
import java.nio.file.*;

public class GerenciadorDeDados {
    private static final Path Users_Path = Paths.get(System.getProperty("user.dir"), "UsersData.txt");
    private static final Path Events_Path = Paths.get(System.getProperty("user.dir"), "EventsData.txt");

    //Bloco para inicializar estaticamente os arquivos
    static {
        criarArquivo(Users_Path);
        criarArquivo(Events_Path);
    }

    private static void criarArquivo(Path caminho) {
        try {
            if (!Files.exists(caminho)) {
                Files.createFile(caminho);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo: " + caminho.getFileName() + ": " + e.getMessage());
        }
    }
}
