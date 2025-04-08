package com.sge.negocio.entidade;

import com.sge.negocio.entidade.ingresso.Ingresso;
import com.sge.negocio.excecao.NenhumEventoCriadoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que representa um usuário.
 * Contém nomeCompleto, nomeUsuario, email, telefone, qtde, ID, senha,
 *         ehAnfitriao, eventosCriados, ingressos, eventosParticipando.
 *
 * @author João Francisco
 */
public class Usuario {
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String telefone;
    private static int qtde = 1;
    private final int ID;
    private String senha;
    private boolean ehAnfitriao = false;
    private ArrayList<Evento> eventosCriados;
    private ArrayList<Ingresso> ingressos;
    private ArrayList<Evento> eventosParticipados;


    /**
     *Construtor da classe usuario.
     *
     * @param nomeCompleto Nome completo do usuário.
     * @param nomeUsuario Username do usuário
     * @param email E-mail do usuário.
     * @param telefone Telefone do usuário.
     * @param senha senha de acesso do usuário.
     */
    public Usuario(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.ID = qtde++;
        this.eventosCriados = new ArrayList<>();
        this.eventosParticipados = new ArrayList<>();
        this.ingressos = new ArrayList<>();
    }

    /**
     * Retorna os dados do usuário formatado como uma String.
     *
     * @return usuário formatado.
     */
    public String usuarioFormatado() {
        System.out.println("--------------------------------------------");
        return ID + " " + nomeCompleto + " " + nomeUsuario + " " + email + " " + telefone + " " + senha;
    }

    /**
     *
     * @return Lista os eventos criados pelo usuário.
     * @throws NenhumEventoCriadoException Caso o usuário não tenha eventos criados.
     */
    public List<Evento> getEventosCriados() throws NenhumEventoCriadoException {
        if (eventosCriados == null || eventosCriados.isEmpty()) {
            throw new NenhumEventoCriadoException();
        }
        return eventosCriados;
    }

    /**
     * Adiciona um ingresso no ArrayList de ingressos
     * @param ingresso ingresso a ser adicionado
     */
    public void adicionarIngresso(Ingresso ingresso){
        this.ingressos.add(ingresso);
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

    public ArrayList<Ingresso> getIngressos() {
        return ingressos;
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

    public int getID() {
        return ID;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Ingresso> getMeusIngressos() {
        return ingressos;
    }

    public  ArrayList<Evento> getEventosParticipados(){
        return eventosParticipados;
    }

    public boolean getEhAnfitriao() {
        return ehAnfitriao;
    }
    public void setEhAnfitriao(boolean ehAnfitriao) {
        this.ehAnfitriao = ehAnfitriao;
    }

    /**
     * Adiciona um evento no ArrayList dos eventos que o usuário participa, caso esse evento exista
     * @param evento evento a ser adicionado
     */
    public void participarDoEvento(Evento evento) {
        if (!eventosParticipados.contains(evento)) {
            eventosParticipados.add(evento);
        }
    }
}