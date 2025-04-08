package testes.integracao;
import static org.junit.jupiter.api.Assertions.*;

import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.EventoDuplicadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class IntegracaoCadastroEventoTest {
    private SGE fachada;

    @BeforeEach
    public void setUp() {
        fachada = SGE.getInstancia();
    }

    @Test
    public void testCadastroEListagemDeEventos() throws FormularioEventoInvalidoException, EventoDuplicadoException {
        Usuario usuario = new Usuario("João Silva", "joao123", "joao@email.com", "87999999999", "senha123");
        Endereco endereco = new Endereco("SP", "São Paulo", "12345-678", "Centro", "Rua A", 100);
        LocalDate dataEvento = LocalDate.of(2025, 5, 22);
        LocalDateTime horaInicio = LocalDateTime.of(2025, 5, 22, 13, 0);
        LocalDateTime horaFim = LocalDateTime.of(2025, 5, 22, 17, 0);
        Evento evento = new Evento("Show", "Show de rock", "Música", endereco, dataEvento,horaInicio,horaFim, 100, 55, usuario);
        // Cadastrando um evento
        //fachada.cadastrarUsuario(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail());
        fachada.cadastrarEvento(evento.getTitulo(),evento.getDescricao(), evento.getCategoria(), evento.getEndereco(),evento.getData(),evento.getHoraInicio(),evento.getHoraFim(),evento.getQtdeIngressos(),evento.getValorBase(),evento.getAnfitriao());

        // Listando eventos
        List<Evento> eventos = fachada.ListarEventos();
        assertEquals(1, eventos.size());
        assertEquals("Show", eventos.get(0).getTitulo());
    }
}