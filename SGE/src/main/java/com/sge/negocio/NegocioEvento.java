package com.sge.negocio;

import com.sge.dados.GerenciadorDeDados;
import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class NegocioEvento {
    private IRepositorioEventos repositorioEventos;

    public NegocioEvento(IRepositorioEventos repositorioEventos) {
        this.repositorioEventos = repositorioEventos;
    }

    public void inserir(Evento evento) {
        repositorioEventos.inserir(evento);
    }
    public void alterar(Evento evento) {
        repositorioEventos.alterar(evento);
    }
    public void remover(Evento evento) {
        repositorioEventos.remover(evento);
    }



}
