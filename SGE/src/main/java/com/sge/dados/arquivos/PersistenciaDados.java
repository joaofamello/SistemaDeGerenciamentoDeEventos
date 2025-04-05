package com.sge.dados.arquivos;

import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
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


public class PersistenciaDados implements IPersistenciaDados {

    private final NegocioUsuario negocioUsuario;
    private final NegocioEvento negocioEvento;

    public PersistenciaDados() {
        negocioUsuario = new NegocioUsuario(new RepositorioUsuariosArrayList());
        negocioEvento = new NegocioEvento(new RepositorioEventosArrayList());
    }

    //Salvar os usuários no arquivo .txt
    @Override
    public void salvarUsuarios(ArrayList<Usuario> usuarios){
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get("usuarios.txt"))) {
            for (Usuario usuario : usuarios) {
                String linha = usuario.getID()+ ";" + usuario.getNomeCompleto() + ";" + usuario.getNomeUsuario() + ";" + usuario.getEmail() + ";" + usuario.getTelefone() + ";" + usuario.getSenha();
                escritor.write(usuario.toString());
                escritor.newLine();
            }
        } catch (IOException e){
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    //Carregar os usuarios do arquivo no Array
    @Override
    public ArrayList<Usuario> carregarUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader leitor = Files.newBufferedReader(GerenciadorDeDados.getPasta_Usuarios())){
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] campo = linha.split(";");
                if (campo.length == 5) { // Verifica se todos os campos estão presentes
                    String nomeCompleto = campo[0];
                    String nomeUsuario = campo[1];
                    String email = campo[2];
                    String telefone = campo[3];
                    String senha = campo[4];

                    // Criando o usuário e adicionando ao vetor
                    Usuario usuario = new Usuario(nomeCompleto, nomeUsuario, email, telefone, senha);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e){
            System.err.println("Erro ao carregar Usuários");
        }
        return usuarios;
    }

   //Salvar os eventos no arquivo .txt
   @Override
    public void salvarEventos(ArrayList<Evento> eventos) {
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get("eventos.txt"))) {
            for (Evento evento : eventos) {
                // Formantando o endereco em uma unica string
                String enderecoFormatado = evento.getEndereco().getEstado() + "," + evento.getEndereco().getCidade() + "," + evento.getEndereco().getCep() + "," + evento.getEndereco().getBairro() + "," + evento.getEndereco().getRua() + "," + evento.getEndereco().getNumero();

                String linha = evento.getTitulo() + ";" + evento.getDescricao() + ";" + evento.getCategoria() + ";" + enderecoFormatado + ";" + evento.getData() + ";" + evento.getHoraInicio() + ";" + evento.getHoraFim() + ";" + evento.getQtdeIngressos() + ";" + evento.getAnfitriao().getID();
                escritor.write(linha);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    //Carregar os eventos do arquivo e colocar no Array
    @Override
    public ArrayList<Evento> carregarEventos() {
        ArrayList<Evento> eventos = new ArrayList<>();
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
