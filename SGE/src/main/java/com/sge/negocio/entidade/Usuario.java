package com.sge.negocio.entidade;

import com.sge.negocio.excecao.NenhumEventoCriadoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um usuario.
 * Contém nomeCompleto, nomeUsuario, email, telefone, qtde, ID, sanha,
 *         ehAnfitriao, eventosCriados, eventosParticipando.
 *
 * @author João Francisco
 */
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
    public static List<Evento> getEventosCriados() throws NenhumEventoCriadoException {
        if (eventosCriados == null){
            throw new NenhumEventoCriadoException();
        }
        return eventosCriados;
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

    /**
     *
     * @param ehAnfitriao ehAnfitriao é atualizado para indicar se o usuario
     *                    pode ou naõ realizar a criação ou gerenciamento de eventos.
     */
    public static void setEhAnfitriao(boolean ehAnfitriao) {
        Usuario.ehAnfitriao = ehAnfitriao;
    }


}