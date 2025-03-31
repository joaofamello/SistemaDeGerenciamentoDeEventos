package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GerenciadorEntrada {
    private Scanner sc = new Scanner(System.in);
    private Usuario usuario;

    public GerenciadorEntrada(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento criarEvento(){
        String titulo = sc.nextLine();
        String descricao = sc.nextLine();
        String categoria = sc.nextLine();
        Endereco endereco = criarEndereco();
        LocalDate data = LocalDate.parse(sc.nextLine());
        LocalDateTime horaInicio = LocalDateTime.parse(sc.nextLine());
        LocalDateTime horaFim = LocalDateTime.parse(sc.nextLine());
        int qtdeIngressos = sc.nextInt();
        return new Evento(titulo, descricao, categoria, endereco, data, horaInicio, horaFim, qtdeIngressos, usuario);
    }

    public Endereco criarEndereco(){
        String rua = sc.nextLine();
        String bairro = sc.nextLine();
        int numero = sc.nextInt();
        String cidade = sc.nextLine();
        String estado = sc.nextLine();
        String cep = sc.nextLine();
        String pais = sc.nextLine();
        return new Endereco (rua, bairro, numero, cep, cidade, estado, pais);
    }

    public Usuario cadastrarUsuario(){
        String nomeCompleto = sc.nextLine();
        String nomeUsuario = sc.nextLine();
        String email = sc.nextLine();
        String telefone = sc.nextLine();
        String senha = sc.nextLine();
        return new Usuario(nomeCompleto, nomeUsuario, email, telefone, senha);
    }

    public String recebeString(){
        return sc.nextLine();
    }

    public double recebeDouble(){
        return sc.nextDouble();
    }

    public int recebeInt(){
        return sc.nextInt();
    }
}
