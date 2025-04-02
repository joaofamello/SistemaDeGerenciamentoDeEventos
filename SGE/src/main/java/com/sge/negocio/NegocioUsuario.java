package com.sge.negocio;

import com.sge.dados.GerenciadorDeDados;
import com.sge.dados.usuarios.IRepositorioUsuarios;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.negocio.entidade.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NegocioUsuario {
    private IRepositorioUsuarios repositorioUsuarios;

    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }
    
    public void inserir(Usuario usuario) {
        repositorioUsuarios.inserir(usuario);
    }

    public Usuario buscarUsuariosPorID(int ID) {
        return repositorioUsuarios.buscarUsuariosPorID(ID);
    }

}
