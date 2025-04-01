package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

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
            for(Usuario usuario : usuarios){
                escritor.write(usuario.toString());
                escritor.newLine();
            }
        } catch (IOException e){
            e.getMessage();
        }
    }
}
