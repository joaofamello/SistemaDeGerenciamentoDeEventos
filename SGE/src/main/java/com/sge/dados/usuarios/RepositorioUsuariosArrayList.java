package com.sge.dados.usuarios;

import com.sge.negocio.entidade.Usuario;

import java.util.ArrayList;
import java.util.Scanner;

public class RepositorioUsuariosArrayList implements IRepositorioUsuarios {

    private final Scanner sc = new Scanner(System.in);
    private ArrayList<Usuario> usuarios;


    public RepositorioUsuariosArrayList() { usuarios = new ArrayList<Usuario>();}

    @Override
    public void inserir(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarUsuariosPorID(int id) {
        Usuario usuarioProcurado = null;
        for (Usuario usuario : usuarios) {
            if(usuario.getID() == id) {
                usuarioProcurado = usuario;
                break;
            }
        }
        return usuarioProcurado;
    }

    public void alterar(Usuario usuario){
        System.out.println("\nQual é a informação que deseja alterar?");
        System.out.println("\nSelecione entre: ");
        System.out.println("\nUsername, email, telefone ou senha");
        String op;
        switch (op = sc.nextLine()){
            case "Username":
                usuario.setNomeUsuario(usuario.getNomeUsuario());
                break;
            case "Email":
                usuario.setEmail(usuario.getEmail());
                break;

            case "Senha":
                usuario.setSenha(usuario.getSenha());
                break;

            case "Telefone":
                usuario.setTelefone(usuario.getTelefone());
                break;
        }
    }

    @Override
    public ArrayList<Usuario> listar() {
        // Retorna uma cópia da lista para evitar modificações externas
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario buscarUsuariosPorNome(String nome) {
        Usuario usuarioProcurado = null;
        for (Usuario usuario : usuarios) {
            if(usuario.getNomeUsuario().equals(nome)) {
                usuarioProcurado = usuario;
                break;
            }
        }
        return usuarioProcurado;
    }
}