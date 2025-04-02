package com.sge.dados.eventos;
import com.sge.dados.GerenciadorDeDados;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.GerenciadorEntrada;
import com.sge.negocio.entidade.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEventosArrayList implements IRepositorioEventos {

    private ArrayList<Evento> eventos;

    public RepositorioEventosArrayList() {
        eventos = new ArrayList<Evento>();
    }

    @Override
    public void inserir(Evento evento) {
        eventos.add(evento);
    }

    @Override
    public void alterar(Evento evento) {
        int index = eventos.indexOf(evento);
        eventos.set(index, evento);
    }

    @Override
    public void remover(Evento evento) {
        int index = eventos.indexOf(evento);
        eventos.remove(index);
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    @Override
    public void salvarEventos(List<Evento> eventos) {
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get("eventos.txt"))) {
            for (Evento evento : eventos) {
                //Formantando o endereco em uma unica string
                String enderecoFormatado = evento.getEndereco().getEstado() + "," + evento.getEndereco().getCidade() + "," + evento.getEndereco().getCep() + "," + evento.getEndereco().getBairro() + "," + evento.getEndereco().getRua() + "," + evento.getEndereco().getNumero();

                String linha = evento.getTitulo() + ";" + evento.getDescricao() + ";" + evento.getCategoria() + ";" + enderecoFormatado + ";" + evento.getData() + ";" + evento.getHoraInicio() + ";" + evento.getHoraFim() + ";" + evento.getQtdeIngressos() + ";" + evento.getAnfitriao().getID();
                escritor.write(linha);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    @Override
    public List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        RepositorioUsuariosArrayList repositorioUsuarios = new RepositorioUsuariosArrayList();
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


                    Usuario anfitriao = repositorioUsuarios.buscarUsuariosPorID(anfitriaoID);

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
