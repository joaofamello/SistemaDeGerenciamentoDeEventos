package com.sge.negocio.entidade;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe que representa um evento.
 * Contém numero, ID, titulo, descrição, categoria, endereço, data, horaInicio, horaFim, qtdIngressos, qtdeIngressosVendidos, valorBaseIngresso, participantes e estado.
 *
 * @author João Francisco
 */
public class Evento {
    private static int numero;
    private final int ID;
    private String titulo;
    private String descricao;
    private String categoria;
    private Endereco endereco;
    private LocalDate data;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    private int qtdeIngressos;
    private int qtdeIngressosVendidos;
    private Usuario anfitriao;
    private double valorBaseIngresso;
    private ArrayList<Usuario> participantes;
    private boolean estado;

    /**
     *Construtor da classe evento.
     *
     * @param titulo Titulo do evento.
     * @param descricao Descrição do evento.
     * @param categoria Categoria do evento.
     * @param endereco Endereço do evento.
     * @param data Data do evento.
     * @param horaInicio Hora de inicio do evento.
     * @param horaFim Hora do fim do evento.
     * @param qtdeIngressos Quantidade de ingreços do evento.
     * @param valorBase Valor base para o ingresso.
     * @param anfitriao Quem está sediando o evento
     */
    //construtor
    public Evento(String titulo, String descricao, String categoria, Endereco endereco, LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim, int qtdeIngressos, double valorBase ,Usuario anfitriao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.endereco = endereco;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.qtdeIngressos = qtdeIngressos;
        this.anfitriao = anfitriao;
        this.ID = ++numero;
        this.valorBaseIngresso = valorBase;
        this.qtdeIngressosVendidos = 0;
        this.estado = true;
        this.participantes = new ArrayList<>();
    }

    //getters e setters
    public int getID() {
        return ID;
    }

    public static int getNumero() {
        return numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    public int getQtdeIngressos() {
        return qtdeIngressos;
    }

    public void setQtdeIngressos(int qtdeIngressos) {
        this.qtdeIngressos = qtdeIngressos;
    }

    public Usuario getAnfitriao() {
        return anfitriao;
    }

    public ArrayList<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Usuario participante) {
        this.participantes.add(participante);
    }

    public LocalDateTime getDataHoraInicio() {
        return horaInicio;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getQtdeIngressosVendidos() {
        return qtdeIngressosVendidos;
    }
    public void setQtdeIngressosVendidos(int qtde) {
         this.qtdeIngressosVendidos = this.qtdeIngressosVendidos - qtde;
    }

    public void incrementaIngressosVendidos() {
        this.qtdeIngressosVendidos += 1;
    }
    public void setValorBase(double valorBase) {
        this.valorBaseIngresso = valorBase;
    }

    public int getIngressosDisponiveis() {
        return qtdeIngressos - qtdeIngressosVendidos;
    }

    public double getValorBase(){
        return valorBaseIngresso;
    }

}
