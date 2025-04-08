package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

public abstract class Ingresso {
    private Usuario participante;
    private final Evento evento;
    private double valorBase;
    private final int ID;
    private static int numero = 1;
    private boolean vendido;

    public Ingresso(Evento evento, double valorBase) {
        this.evento = evento;
        this.valorBase = valorBase;
        this.ID = evento.getQtdeIngressosVendidos() + 1;
        this.vendido = false;
    }

    public Ingresso(double valorBase) {
        this.evento = null;
        this.valorBase = valorBase;
        this.ID = -1;
        this.vendido = false;
    }

    public boolean isUsado(){
        return vendido;
    }

    public abstract double calcularValorFinal();

    public void vender (Usuario comprador) {
        this.participante = comprador;
        this.vendido = true;

    }

    public Usuario getParticipante() {
        return participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public double getValorBase() {
        return valorBase;
    }

    public int getID() {
        return ID;
    }



}
