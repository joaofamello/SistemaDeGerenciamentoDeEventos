package com.sge.negocio.entidade;

import com.sge.negocio.excecao.NenhumEventoCriadoException;

import java.util.ArrayList;
import java.util.List;


public class Usuario {
    private final String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String telefone;
    private static int qtde = 1;
    private final int ID;
    private String senha;
    private static boolean ehAnfitriao = false;
    private static ArrayList<Evento> eventosCriados;
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

    public String usuarioFormatado() {
        System.out.println("--------------------------------------------");
        return ID + " " + nomeCompleto + " " + nomeUsuario + " " + email + " " + telefone + " " + senha;
    }

    public static List<Evento> getEventosCriados() throws NenhumEventoCriadoException {
        if (eventosCriados == null){
            throw new NenhumEventoCriadoException();
        }
        return eventosCriados;
    }

    public void comprarIngresso(Evento evento, Usuario usuario) {
        for(Ingresso ingresso : evento.getParticipantes()){
            if(!ingresso.isVendido()){
                ingresso.setParticipante(usuario);
                break;
            }
        }
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