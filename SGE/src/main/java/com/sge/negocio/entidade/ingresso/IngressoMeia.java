package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;

public class IngressoMeia extends Ingresso {
    private static final double descontoMeia = 0.5;

    public IngressoMeia(Evento evento, double valorBase) {
        super(evento, valorBase);
    }

    @Override
    public double calcularValorFinal(){
        return getValorBase() * (1 - descontoMeia); //Retorna o valor com 50% de desconto
    }

}
