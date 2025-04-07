package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;

import java.time.LocalDate;

public class IngressoMeia extends Ingresso {
    private static final double descontoMeia = 0.5;

    public IngressoMeia(Evento evento, double valorBase) {
        super(evento, valorBase);
    }
    public IngressoMeia(double valorBase){
        super(valorBase);
    }

    @Override
    public boolean ehValido(){
        return !super.isUsado() && LocalDate.now().isBefore(this.getEvento().getData());
    }

    @Override
    public double calcularValorFinal(){
        return getValorBase() * (1 - descontoMeia); //Retorna o valor com 50% de desconto
    }

}
