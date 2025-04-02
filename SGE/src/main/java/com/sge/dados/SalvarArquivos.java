package com.sge.dados;

import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.negocio.NegocioUsuario;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.entidade.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.sge.dados.GerenciadorDeDados;


public class SalvarArquivos {

    private NegocioUsuario negocioUsuario;
    private NegocioEvento negocioEvento;

    public SalvarArquivos() {
        negocioUsuario = new NegocioUsuario(new RepositorioUsuariosArrayList());
        negocioEvento = new NegocioEvento(new RepositorioEventosArrayList());
    }

    public List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader leitor = Files.newBufferedReader(GerenciadorDeDados.getPasta_Eventos())) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] campo = linha.split(";");
                if (campo.length == 10) {
                    String Titulo = campo[0];
                    String Descricao = campo[1];
                    String Categoria = campo[2];

                    //Construindo o endereco que foi separado em partes
                    Endereco endereco = new Endereco(campo[4], campo[5], campo[6], campo[7], campo[8], Integer.parseInt(campo[9]));

                    LocalDate data = LocalDate.parse(campo[10]);
                    LocalDateTime horaInicio = LocalDateTime.parse(campo[11]);
                    LocalDateTime horaFim = LocalDateTime.parse(campo[12]);
                    int qtdeIngressos = Integer.parseInt(campo[13]);
                    int anfitriaoID = Integer.parseInt(campo[14]);


                    Usuario anfitriao = negocioUsuario.buscarUsuariosPorID(anfitriaoID);

                    Evento evento = new Evento(Titulo, Descricao, Categoria, endereco, data, horaInicio, horaFim, qtdeIngressos, anfitriao);
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar eventos: " + e.getMessage());
        }
        return eventos;
    }

}
