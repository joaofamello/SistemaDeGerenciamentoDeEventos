package com.sge.negocio.entidade.ingresso;

import com.sge.negocio.entidade.Evento;

public class IngressoPromocional extends Ingresso {
    private final double percentualDesconto;
    IngressoPromocional(Evento evento, double valorBase, double percentualDesconto) {
        super(evento, valorBase);
        this.percentualDesconto = Math.min(percentualDesconto, 0.8); // Limita a 80% de desconto
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * (1 - percentualDesconto); //Retorna valor após calcular o percentual de desconto
    }
}
