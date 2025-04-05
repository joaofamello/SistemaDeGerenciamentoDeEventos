package com.sge.negocio.entidade;

public class Ingresso {
    private Usuario participante;
    private final Evento evento;
    private double valor;
    private final int ID;
    private static int numero = 1;
    private boolean vendido = false;

    public Ingresso(Evento evento, double valor){
        this.participante = null;
        this.evento = evento;
        this.valor = valor;
        this.ID = numero++;
    }

    public Ingresso(Usuario comprador, Evento evento, double valor) {
        this.participante = comprador;
        this.evento = evento;
        this.valor = valor;
        this.ID = numero++;
    }


    public void exibeIngresso(Usuario usuario, Evento evento) {

    }

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
        this.vendido = true;
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

    public boolean isVendido() {
        return vendido;
    }

}
