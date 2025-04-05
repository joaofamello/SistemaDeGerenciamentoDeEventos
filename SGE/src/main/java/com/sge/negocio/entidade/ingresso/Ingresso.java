package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;

public abstract class Ingresso {
    private Usuario participante;
    private final Evento evento;
    private double valorBase;
    private final int ID;
    private static int numero = 1;
    private boolean vendido = false;

    public Ingresso(Evento evento, double valorBase) {
        this.evento = evento;
        this.valorBase = valorBase;
        this.ID = numero++;
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

    public boolean isVendido() {
        return vendido;
    }

    public void exibirDetalhes() {
        System.out.println("------------------------");
        System.out.println("Ingresso #" + ID);
        System.out.println("Evento: " + evento.getTitulo());
        System.out.println("Tipo: " + this.getClass().getSimpleName());
        System.out.println("Valor: R$" + calcularValorFinal());
        System.out.println("Status: " + (vendido ? "Vendido" : "Disponível"));
        System.out.println("------------------------");
    }

}
