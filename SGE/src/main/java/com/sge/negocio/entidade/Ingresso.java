package com.sge.negocio.entidade;

public class Ingresso {
    private final Usuario comprador;
    private final Evento evento;
    private double valor;
    private final int ID;
    private static int numero = 1;

    public Ingresso(Usuario comprador, Evento evento, double valor) {
        this.comprador = comprador;
        this.evento = evento;
        this.valor = valor;
        this.ID = numero++;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Evento getEvento() {
        return evento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getID() {
        return ID;
    }

    public static int getNumero() {
        return numero;
    }
}
