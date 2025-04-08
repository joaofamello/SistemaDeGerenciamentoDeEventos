package testes.unidade;
import static org.junit.jupiter.api.Assertions.*;

import com.sge.negocio.entidade.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.excecao.EventoNaoEncontradoException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventoBuscaTest {
    private NegocioEvento negocioEvento;

    @BeforeEach
    public void setUp() {
        negocioEvento = new NegocioEvento(new RepositorioEventosArrayList());
    }

    @Test
    public void testBuscarEventoPorTitulo() throws Exception {
        Usuario usuario = new Usuario("João Silva", "joao123", "joao@email.com", "87999999999", "senha123");
        Endereco endereco = new Endereco("SP", "São Paulo", "12345-678", "Centro", "Rua A", 100);
        LocalDate dataEvento = LocalDate.of(2025, 5, 22);
        LocalDateTime horaInicio = LocalDateTime.of(2025, 5, 22, 13, 0);
        LocalDateTime horaFim = LocalDateTime.of(2025, 5, 22, 17, 0);
        Evento evento = new Evento("Show", "Show de rock", "Música", endereco, dataEvento,horaInicio,horaFim, 100, 55, usuario);

        negocioEvento.inserir(evento);

        assertEquals(evento, negocioEvento.buscarPorTitulo("Show").get(0));
    }

    @Test
    public void testBuscarEventoPorTituloInexistente() throws EventoNaoEncontradoException {

        negocioEvento.buscarPorTitulo("Show da algazarra");
    }
}