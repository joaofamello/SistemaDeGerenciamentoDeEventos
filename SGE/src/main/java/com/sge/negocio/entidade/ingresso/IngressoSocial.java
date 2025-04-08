package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;


public class IngressoSocial extends Ingresso {
    private static final double percentualDesconto = 0.15;

    public IngressoSocial(Evento evento, double valorBase) {
        super(evento, valorBase);

    }

    public IngressoSocial(double valorBase){
        super(valorBase);
    }


    @Override
    public double calcularValorFinal() {
        return getValorBase() * (1 - percentualDesconto);
    }
}
