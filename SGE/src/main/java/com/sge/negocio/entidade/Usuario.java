package com.sge.negocio.entidade;

import com.sge.negocio.entidade.ingresso.Ingresso;
import com.sge.negocio.excecao.NenhumEventoCriadoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que representa um usuario.
 * Contém nomeCompleto, nomeUsuario, email, telefone, qtde, ID, sanha,
 *         ehAnfitriao, eventosCriados, eventosParticipando.
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
     * @param nomeCompleto NomeCompleto do Usuario.
     * @param nomeUsuario Nome de usuario do Usuario.
     * @param email Email do Usuario.
     * @param telefone Telefone do Usuario.
     * @param senha Senha do Usuario
     */
    //Construtor
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
     * Retorna o usuario formatado em uma unica string.
     *
     * @return Usuario completo formatado.
     */
    public String usuarioFormatado() {
        System.out.println("--------------------------------------------");
        return ID + " " + nomeCompleto + " " + nomeUsuario + " " + email + " " + telefone + " " + senha;
    }

    /**
     *
     * @return Lista com os eventos criados pelo usuario.
     * @throws NenhumEventoCriadoException Se ocorrer o usuario não possui eventos cadastrados.
     */
    public List<Evento> getEventosCriados() throws NenhumEventoCriadoException {
        if (eventosCriados == null || eventosCriados.isEmpty()) {
            throw new NenhumEventoCriadoException();
        }
        return eventosCriados;
    }

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

    public void participarDoEvento(Evento evento) {
        if (!eventosParticipados.contains(evento)) {
            eventosParticipados.add(evento);
        }
    }

    /*public boolean temIngressoValido(Evento evento){
        return this.ingressos.stream().anyMatch(ingresso -> ingresso.pertenceAoEvento(evento) && ingresso.ehValido());
    }*/

}