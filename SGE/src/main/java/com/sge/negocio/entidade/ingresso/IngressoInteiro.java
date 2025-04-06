package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;

import java.time.LocalDate;

public class IngressoInteiro extends Ingresso {

    public IngressoInteiro(Evento evento, double valorBase){
        super(evento, valorBase);
    }

    @Override
    public double calcularValorFinal(){
        return getValorBase(); //Retorna o valor sem desconto
    }

    @Override
    public boolean ehValido(){
        return !super.isUsado() && LocalDate.now().isBefore(this.getEvento().getData());
    }
}
