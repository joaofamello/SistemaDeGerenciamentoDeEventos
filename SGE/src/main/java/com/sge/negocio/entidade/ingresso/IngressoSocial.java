package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;

import java.time.LocalDate;

public class IngressoSocial extends Ingresso {
    private static final double percentualDesconto = 0.15;
    IngressoSocial(Evento evento, double valorBase, double percentualDesconto) {
        super(evento, valorBase);

    }

    @Override
    public boolean ehValido(){
        return !super.isUsado() && LocalDate.now().isBefore(this.getEvento().getData());
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * (1 - percentualDesconto);
    }
}
