package com.sge.negocio.entidade;

//import com.sge.dados.avaliacao.RepositorioParticipacao;
import com.sge.dados.avaliacao.IRepositorioParticipacao;
import com.sge.dados.avaliacao.RepositorioParticipacao;
import com.sge.negocio.entidade.Avaliacao;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.excecao.AvaliacaoInvalidaException;

public class ValidarAvaliacao {
    private final IRepositorioParticipacao repositorio;

    public ValidarAvaliacao(IRepositorioParticipacao repositorio) {
        this.repositorio = repositorio;
    }

    public void validar(Usuario avaliador, Evento evento) throws AvaliacaoInvalidaException {
        if (!repositorio.isParticipante(avaliador, evento)) {
            throw new AvaliacaoInvalidaException("Usuário não participou deste evento");
        }

        if (repositorio.jaAvaliou(avaliador, evento)) {
            throw new AvaliacaoInvalidaException("Usuário já avaliou este evento");
        }
    }
}
