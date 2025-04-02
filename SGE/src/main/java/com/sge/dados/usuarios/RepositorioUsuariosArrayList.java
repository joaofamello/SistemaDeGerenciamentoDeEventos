package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;
import com.sge.dados.GerenciadorDeDados;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuariosArrayList implements IRepositorioUsuarios {

    private ArrayList<Usuario> usuarios;

    private RepositorioUsuariosArrayList() { usuarios = new ArrayList<Usuario>();}

    @Override
    public void inserir(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscar(int id) {
        Usuario usuario = null;
        for (Usuario usuario1 : usuarios) {
            if(usuario.getID() == id) {
                usuario = usuario1;
                break;
            }
        }
        return usuario;
    }

    @Override
    public ArrayList<Usuario> listar() {
        return null;
    }

    @Override
    public void salvarUsuarios(List<Usuario> usuarios){
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get("usuarios.txt"))) {
            for (Usuario usuario : usuarios) {
                String linha = usuario.getID()+ ";" + usuario.getNomeCompleto() + ";" + usuario.getNomeUsuario() + ";" + usuario.getEmail() + ";" + usuario.getTelefone() + ";" + usuario.getSenha();
                escritor.write(usuario.toString());
                escritor.newLine();
            }
        } catch (IOException e){
            System.err.println("Erro ao salvar usuarios: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> carregarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
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
            e.getMessage();
        }
        return usuarios;
    }
}
