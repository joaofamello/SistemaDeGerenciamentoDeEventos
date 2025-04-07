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
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaDados implements IPersistenciaDados {

    //Salvar os usuários no arquivo .txt
    @Override
    public void salvarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get("SGE/src/main/java/com/sge/dados/bancoDeDados/UsersData.txt"))) {
            for (Usuario usuario : usuarios) {
                String linha = String.join(";",
                        String.valueOf(usuario.getID()),
                        usuario.getNomeCompleto(),
                        usuario.getNomeUsuario(),
                        usuario.getEmail(),
                        usuario.getTelefone(),
                        usuario.getSenha()
                );
                escritor.write(linha);
                escritor.newLine();
                escritor.newLine();


            }
        } catch (IOException e) {
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
                String[] campos = linha.split(";");
                // Agora esperando 6 campos (ID + 5 informações)
                if (campos.length == 6) {
                    int id = Integer.parseInt(campos[0].trim());
                    String nomeCompleto = campos[1];
                    String nomeUsuario = campos[2];
                    String email = campos[3];
                    String telefone = campos[4];
                    String senha = campos[5];

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
   public void salvarEventos(List<Evento> eventos) {
           try (BufferedWriter escritor = Files.newBufferedWriter(
               Paths.get("SGE/src/main/java/com/sge/dados/bancoDeDados/EventsData.txt"),
               StandardOpenOption.CREATE,
               StandardOpenOption.TRUNCATE_EXISTING,  // Sobrescreve o arquivo
               StandardOpenOption.WRITE)) {

           for (Evento evento : eventos) {
               String linha = String.join(";",
                       evento.getTitulo(),
                       evento.getDescricao(),
                       evento.getCategoria(),
                       evento.getEndereco().getEstado(),
                       evento.getEndereco().getCidade(),
                       evento.getEndereco().getCep(),
                       evento.getEndereco().getBairro(),
                       evento.getEndereco().getRua(),
                       String.valueOf(evento.getEndereco().getNumero()),
                       evento.getData().toString(),
                       evento.getHoraInicio().toString(),
                       evento.getHoraFim().toString(),
                       String.valueOf(evento.getQtdeIngressos()),
                       String.valueOf(evento.getValorBase()),
                       String.valueOf(evento.getAnfitriao().getID()),
                       evento.RetornarEstado()

               );
               System.out.println("Salvando linha: " + linha);
               escritor.write(linha);
               escritor.newLine();
           }
       } catch (IOException e) {
           System.err.println("Erro ao salvar eventos: " + e.getMessage());
           e.printStackTrace();
       }
       System.out.println("Eventos salvos com sucesso!");
   }





    //Carregar os eventos do arquivo e colocar no Array
    @Override
    public ArrayList<Evento> carregarEventos(ArrayList<Usuario> usuarios) {
        Usuario anfitriao = null;
        ArrayList<Evento> eventos = new ArrayList<>();
        try (BufferedReader leitor = Files.newBufferedReader(Paths.get("SGE/src/main/java/com/sge/dados/bancoDeDados/EventsData.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] campo = linha.split(";");
                if (campo.length >= 15) {
                    String Titulo = campo[0];
                    String Descricao = campo[1];
                    String Categoria = campo[2];

                    // Endereço (6 partes - 3 a 8)
                    Endereco endereco = new Endereco(
                            campo[3],  // estado
                            campo[4],  // cidade
                            campo[5],  // cep
                            campo[6],  // bairro
                            campo[7],  // rua
                            Integer.parseInt(campo[8])  // numero
                    );

                    LocalDate data = LocalDate.parse(campo[9]);
                    LocalDateTime horaInicio = LocalDateTime.parse(campo[10]);
                    LocalDateTime horaFim = LocalDateTime.parse(campo[11]);
                    int qtdeIngressos = Integer.parseInt(campo[12]);
                    double valorBase = Double.parseDouble(campo[13]);
                    int anfitriaoID = Integer.parseInt(campo[14]);
                    String estadoStr = campo.length > 15 ? campo[15] : "Ativo";

                    for(Usuario usuario : usuarios){
                        if(usuario.getID() == anfitriaoID){
                            anfitriao = usuario;
                        }
                    }

                    Evento evento = new Evento(Titulo, Descricao, Categoria, endereco,
                            data, horaInicio, horaFim, qtdeIngressos, valorBase, anfitriao);

                    // Configura estado adicional se necessário
                    if (estadoStr.equals("Inativo")) {
                        evento.setEstado(false);
                    }

                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar eventos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao processar eventos: " + e.getMessage());
            e.printStackTrace();
        }
        return eventos;
    }

    public void salvarParticipado(List<Usuario> usuarios) {
        try (BufferedWriter escritor = Files.newBufferedWriter(GerenciadorDeDados.getPasta_participado())) {
            for (Usuario usuario : usuarios) {
                if (usuario.getEventosParticipados().isEmpty()) continue; // Pula usuários sem eventos

                StringBuilder linha = new StringBuilder();
                linha.append(usuario.getID());

                for (Evento evento : usuario.getEventosParticipados()) {
                    linha.append(";").append(evento.getTitulo());
                }

                escritor.write(linha.toString());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar participações: " + e.getMessage());
        }
    }

    public void carregarParticipado(List<Usuario> usuarios, List<Evento> eventos) {
        try (BufferedReader leitor = Files.newBufferedReader(GerenciadorDeDados.getPasta_participado())) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] campos = linha.split(";");

                // Espera pelo menos um usuário e um evento
                if (campos.length >= 2) {
                    int usuarioID = Integer.parseInt(campos[0]);

                    // Busca o usuário correspondente
                    Usuario usuario = usuarios.stream()
                            .filter(u -> u.getID() == usuarioID)
                            .findFirst()
                            .orElse(null);

                    if (usuario == null) continue;

                    // Associa cada evento ao usuário
                    for (int i = 1; i < campos.length; i++) {
                        String tituloEvento = campos[i];

                        Evento evento = eventos.stream()
                                .filter(e -> e.getTitulo().equalsIgnoreCase(tituloEvento))
                                .findFirst()
                                .orElse(null);

                        if (evento != null) {
                            usuario.participarDoEvento(evento);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar participações: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ID de usuário inválido em Participado.txt: " + e.getMessage());
        }
    }

    public void salvarParticipantes(List<Evento> eventos) {
        try (BufferedWriter escritor = Files.newBufferedWriter(GerenciadorDeDados.getPasta_Participantes())) {
            for (Evento evento : eventos) {
                StringBuilder linha = new StringBuilder();
                linha.append(evento.getTitulo());

                for (Usuario usuario : evento.getParticipantes()) {
                    linha.append(";").append(usuario.getID());
                }

                escritor.write(linha.toString());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar participações: " + e.getMessage());
        }
    }

    public void carregarParticipantes(List<Evento> eventos, List<Usuario> usuarios) {
        try (BufferedReader leitor = Files.newBufferedReader(GerenciadorDeDados.getPasta_Participantes())) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] campos = linha.split(";");
                if (campos.length < 2) continue; // Evento sem participantes

                String tituloEvento = campos[0];

                // Localiza o evento correspondente pelo título
                Evento evento = eventos.stream()
                        .filter(e -> e.getTitulo().equalsIgnoreCase(tituloEvento))
                        .findFirst()
                        .orElse(null);

                if (evento == null) continue;

                // Percorre os IDs dos participantes
                for (int i = 1; i < campos.length; i++) {
                    try {
                        int usuarioId = Integer.parseInt(campos[i]);
                        Usuario usuario = usuarios.stream()
                                .filter(u -> u.getID() == usuarioId)
                                .findFirst()
                                .orElse(null);

                        if (usuario != null) {
                            evento.participarDoEventoADM(usuario);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("ID de usuário inválido: " + campos[i]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar participações: " + e.getMessage());
        }
    }



}
