package com.sge.negocio.excecao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CancelamentoProibidoException extends SGEException {
    private final LocalDateTime dataEvento;

    public CancelamentoProibidoException(LocalDateTime dataEvento) {
      super("Cancelamento não permitido: faltam menos de 48 horas para o evento.");
      this.dataEvento = dataEvento;
    }

    // Metodo para informar as horas restantes para que o evento comece
    public String getTempoRestante() {
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), dataEvento);
        return "Horas restantes para o evento: " + horasRestantes + " (mínimo necessário: 48)";
    }
}
