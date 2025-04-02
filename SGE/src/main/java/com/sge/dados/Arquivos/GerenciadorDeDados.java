package com.sge.dados.Arquivos;

import java.io.*;
import java.nio.file.*;

public class GerenciadorDeDados {
    private static final Path Diretorio = Paths.get(System.getProperty("user.dir"), "SGE/src/main/java/com/sge/dados/BancoDeDados");
    private static final Path Pasta_Usuarios = Diretorio.resolve("UsersData.txt");
    private static final Path Pasta_Eventos = Diretorio.resolve("EventsData.txt");

    //Bloco para inicializar estaticamente os arquivos
    static {
        criarDiretorio(Diretorio);
        criarArquivo(Pasta_Usuarios);
        criarArquivo(Pasta_Eventos);
    }

    private static void criarDiretorio(Path diretorio) {
        try{
            if(!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
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
        return Pasta_Eventos;
    }
}
