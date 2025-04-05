package com.sge.dados.avaliacao;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.entidade.Evento;

public interface IRepositorioParticipacao {
    boolean isParticipante(Usuario usuario, Evento evento);
    boolean jaAvaliou(Usuario usuario, Evento evento);
    void registrarParticipacao(Usuario usuario, Evento evento);
    void registrarAvaliacao(Usuario usuario, Evento evento, int nota, String comentario);

}
