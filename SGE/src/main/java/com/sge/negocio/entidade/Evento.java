package com.sge.negocio.entidade;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {
    private static int numero;
    private final int ID;
    private String titulo;
    private String descricao;
    private String categoria;
    private Endereco endereco;
    private LocalDate data; //Data do evento
    private LocalDateTime horaInicio; //Horario que comeca
    private LocalDateTime horaFim; //Horario que termina
    private int qtdeIngressos;
    private Usuario anfitriao;
    private ArrayList<Ingresso> participantes;
    private boolean estado;

    //construtor
    public Evento(String titulo, String descricao, String categoria, Endereco endereco, LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim, int qtdeIngressos, Usuario anfitriao) {
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
        this.estado = true;
    }

    //formatação do evento
    public String eventoFormatado(){
        System.out.println("--------------------------------------------");
        return titulo + "; " + descricao + "; " + categoria + "; " + endereco.enderecoFormatado() + "; " + data + "; " + horaInicio + "; " + horaFim + "; " + qtdeIngressos + "; " + anfitriao.getNomeCompleto() + "; ";
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

    public void setAnfitriao(Usuario anfitriao) {
        this.anfitriao = anfitriao;
    }

    public ArrayList<Ingresso> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Ingresso> participantes) {
        this.participantes = participantes;
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


}
