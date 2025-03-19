package com.sge;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {
    private static int numero;
    private int ID;
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private String local;
    private LocalDate data; //Data do evento
    private LocalDateTime horaInicio; //Horario que comeca
    private LocalDateTime horaFim; //Horario que termina
    private int qtdeIngressos;
    private Usuario anfitriao;
    private ArrayList<Ingresso> participantes;

    public Evento(String titulo, String descricao, Categoria categoria, String local, LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim, int qtdeIngressos, Usuario anfitriao, ArrayList<Ingresso> participantes) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.local = local;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.qtdeIngressos = qtdeIngressos;
        this.anfitriao = anfitriao;
        this.participantes = participantes;
        this.ID = ++numero;
    }

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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
}
