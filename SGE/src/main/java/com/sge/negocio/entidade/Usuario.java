package com.sge.negocio.entidade;

import java.util.ArrayList;

public class Usuario {
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String telefone;
    private static int qtde = 1;
    private int ID;
    private String senha;
    private static boolean ehAnfitriao = false;
    ArrayList<Evento> eventosParticipando;

    //Construtor
    public Usuario(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.ID = qtde++;
    }

    //Esse metodo vai criar o Usuario com ID
    public static Usuario criarUsuarioComID(int ID, String nomeCompleto, String nomeUsuario, String email, String telefone, String senha) {
        Usuario usuario = new Usuario(nomeCompleto, nomeUsuario, email, telefone, senha);
        usuario.ID = ID;
        return usuario;
    }

    //getters e setters
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public static int getQtde() {
        return qtde;
    }

    public int getID() {
        return ID;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Evento> getEventosParticipando() {
        return eventosParticipando;
    }

    public void setEventosParticipando(ArrayList<Evento> eventosParticipando) {
        this.eventosParticipando = eventosParticipando;
    }

    public static boolean getEhAnfitriao() {
        return ehAnfitriao;
    }

    public static void setEhAnfitriao(boolean ehAnfitriao) {
        Usuario.ehAnfitriao = ehAnfitriao;
    }
}