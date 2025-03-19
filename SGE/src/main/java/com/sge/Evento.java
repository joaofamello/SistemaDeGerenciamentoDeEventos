package com.sge;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

public class Evento {
    private String titulo;
    private String descricao;
    private String categoria;
    private String local;
    private LocalDate data; //Data do evento
    private LocalDateTime horaInicio; //Horario que comeca
    private LocalDateTime horaFim; //Horario que termina
    private int qtdeIngressos;
    private double valorIngresso;
    private Usuario anfitriao;
    private List<Usuario> participantes;

    public Evento(String titulo, String descricao, String categoria, String local, LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim, int qtdeIngressos, double valorIngresso, Usuario anfitriao, List<Usuario> participantes) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.local = local;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.qtdeIngressos = qtdeIngressos;
        this.valorIngresso = valorIngresso;
        this.anfitriao = anfitriao;
        this.participantes = participantes;
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

    public double getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(double valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    public Usuario getAnfitriao() {
        return anfitriao;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

}
