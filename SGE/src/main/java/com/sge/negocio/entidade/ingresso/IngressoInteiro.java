package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;

public class IngressoInteiro extends Ingresso {

    public IngressoInteiro(Evento evento, double valorBase){
        super(evento, valorBase);
    }

    public IngressoInteiro(double valorBase){
        super(valorBase);
    }

    @Override
    public double calcularValorFinal(){
        return getValorBase(); //Retorna o valor sem desconto
    }

}
