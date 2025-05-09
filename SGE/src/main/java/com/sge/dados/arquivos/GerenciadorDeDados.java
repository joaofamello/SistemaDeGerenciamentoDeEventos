package com.sge.dados.arquivos;

import java.io.*;
import java.nio.file.*;

public class GerenciadorDeDados {
    private static final Path Diretorio = Paths.get(System.getProperty("user.dir"), "SGE/src/main/java/com/sge/dados/bancoDeDados");
    private static final Path Pasta_Usuarios = Diretorio.resolve("UsersData.txt");
    private static final Path Pasta_Eventos = Diretorio.resolve("EventsData.txt");
    private static final Path Pasta_Participantes = Diretorio.resolve("ParticipantesData.txt");
    private static final Path Pasta_participado = Diretorio.resolve("ParticipadoData.txt");


    //Bloco para inicializar estaticamente os arquivos
    static {
        criarDiretorio();
        criarArquivo(Pasta_Usuarios);
        criarArquivo(Pasta_Eventos);
        criarArquivo(Pasta_Participantes);
        criarArquivo(Pasta_participado);
    }

    private static void criarDiretorio() {
        try{
            if(!Files.exists(GerenciadorDeDados.getDiretorio())) {
                Files.createDirectories(GerenciadorDeDados.getDiretorio());
            }
        } catch(IOException e){
            System.err.println("Erro ao criar diretório: " + e.getMessage());
        }
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

    public static Path getDiretorio() {
        return Diretorio;
    }

    public static Path getPasta_Usuarios() {
        return Pasta_Usuarios;
    }

    public static Path getPasta_Eventos() {
        return Paths.get("SGE/src/main/java/com/sge/dados/bancoDeDados/EventsData.txt");
    }

    public static Path getPasta_Participantes() {
        return Paths.get("SGE/src/main/java/com/sge/dados/bancoDeDados/ParticipantesData.txt");
    }

    public static Path getPasta_participado() {
        return Paths.get("SGE/src/main/java/com/sge/dados/bancoDeDados/ParticipadoData.txt");
    }
}
