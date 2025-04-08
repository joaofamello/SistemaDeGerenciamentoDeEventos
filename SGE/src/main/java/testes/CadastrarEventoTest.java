package testes;
import static org.junit.jupiter.api.Assertions.*;

import java.time.*;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.EventoDuplicadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;

class CadastrarEventoTest {
    private NegocioEvento negocioEvento;

    @BeforeEach
    public void setUp() {
        negocioEvento = new NegocioEvento(new RepositorioEventosArrayList());
    }

    @Test
    public void testCadastrarEvento() throws FormularioEventoInvalidoException, EventoDuplicadoException {
        Usuario usuario = new Usuario("João Silva", "joao123", "joao@email.com", "87999999999", "senha123");
        Endereco endereco = new Endereco("SP", "São Paulo", "12345-678", "Centro", "Rua A", 100);
        LocalDate dataEvento = LocalDate.of(2025, 5, 22);
        LocalDateTime horaInicio = LocalDateTime.of(2025, 5, 22, 13, 0);
        LocalDateTime horaFim = LocalDateTime.of(2025, 5, 22, 17, 0);
        Evento evento = new Evento("Show", "Show de rock", "Música", endereco, dataEvento,horaInicio,horaFim, 100, 55, usuario);

        negocioEvento.inserir(evento);

        assertEquals(1, negocioEvento.listarTodosEventos().size());
    }

    @Test
    public void testCadastrarEventoDuplicado() throws FormularioEventoInvalidoException, EventoDuplicadoException {
        SGE fachada = SGE.getInstancia();
        Usuario usuario = new Usuario("João Silva", "joao123", "joao@email.com", "87999999999", "senha123");
        Endereco endereco = new Endereco("SP", "São Paulo", "12345-678", "Centro", "Rua A", 100);
        LocalDate dataEvento = LocalDate.of(2025, 5, 22);
        LocalDateTime horaInicio = LocalDateTime.of(2025, 5, 22, 13, 0);
        LocalDateTime horaFim = LocalDateTime.of(2025, 5, 22, 17, 0);
        Evento evento1 = new Evento("Festa", "Show de rock", "Música", endereco, dataEvento, horaInicio, horaFim, 100, 55, usuario);


        Evento eventoDuplicado = new Evento("Festa", "Show de rock", "Música", endereco, dataEvento, horaInicio, horaFim, 100, 55, usuario);

        fachada.cadastrarEvento(evento1.getTitulo(),evento1.getDescricao(),evento1.getCategoria(),evento1.getEndereco(), evento1.getData(), evento1.getDataHoraInicio(), evento1.getHoraFim(), evento1.getQtdeIngressos(),evento1.getValorBase(), evento1.getAnfitriao());

        // Tenta cadastrar o evento duplicado e espera a exceção
        EventoDuplicadoException excecao = assertThrows(EventoDuplicadoException.class, () -> {
            fachada.cadastrarEvento(eventoDuplicado.getTitulo(), eventoDuplicado.getDescricao(), eventoDuplicado.getCategoria(), endereco, eventoDuplicado.getData(), eventoDuplicado.getDataHoraInicio(), eventoDuplicado.getHoraFim(), 100, 55.0, usuario);
        });

        assertTrue(excecao.getMessage().contains("Já existe um evento no mesmo local"));
    }
}
