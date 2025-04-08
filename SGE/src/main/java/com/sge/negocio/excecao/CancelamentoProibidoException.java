package com.sge.negocio.excecao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CancelamentoProibidoException extends SGEException {


    public CancelamentoProibidoException(LocalDateTime dataEvento) {
      super("Cancelamento não permitido: faltam menos de 48 horas para o evento.");
    }


}
