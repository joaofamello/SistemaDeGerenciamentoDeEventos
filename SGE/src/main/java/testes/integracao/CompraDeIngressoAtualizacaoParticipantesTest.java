package testes.integracao;

import static org.junit.jupiter.api.Assertions.*;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.entidade.ingresso.Ingresso;
import com.sge.negocio.excecao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Endereco;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class CompraDeIngressoAtualizacaoParticipantesTest {
    private SGE fachada;

    @BeforeEach
    public void setUp() {
        fachada = SGE.getInstancia();
    }

    @Test
    public void CompraIngressoAtualizaParticipanteTest() throws Exception {

        // Usuário e evento
        fachada.cadastrarUsuario("João", "joao123", "joao@email.com", "87999999999", "123456");
        Usuario joao = fachada.LoginUsuario("joao123", "123456");

        Endereco endereco = new Endereco("PE", "Olinda", "00000-000", "Centro", "Rua A", 1);
        LocalDate data = LocalDate.now().plusDays(3);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(10, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(14, 0));
        fachada.cadastrarEvento("Cinema", "Filme novo", "Cultura", endereco, data, inicio, fim, 5, 15.0, joao);

        Evento evento = fachada.ListarEventos().stream().filter(e -> e.getTitulo().equals("Cinema")).findFirst().orElseThrow();

        Ingresso ingresso = new com.sge.negocio.entidade.ingresso.IngressoInteiro(evento, evento.getValorBase());

        fachada.comprarIngresso(joao, evento, ingresso);

        assertTrue(evento.getParticipantes().contains(joao));
        assertTrue(joao.getIngressos().contains(ingresso));
        assertEquals(4, evento.getIngressosDisponiveis());
    }

    @Test
    public void CompraIngressoEventoLotadoFalhaTest() throws FormularioUsuarioInvalidoException, LoginFalhouException, FormularioEventoInvalidoException, EventoDuplicadoException, IngressosEsgotadoException {

        fachada.cadastrarUsuario("João", "joao123", "joao@email.com", "87999999999", "123456");
        fachada.cadastrarUsuario("Jura", "jurandir", "jurandir@email.com", "87999999999", "123456");
        Usuario joao = fachada.LoginUsuario("joao123", "123456");
        Usuario jurandir = fachada.LoginUsuario("jurandir", "123456");
        Endereco endereco = new Endereco("PE", "Olinda", "00000-000", "Centro", "Rua A", 1);
        LocalDate data = LocalDate.now().plusDays(3);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(10, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(14, 0));

        // Evento com apenas 1 ingresso disponível
        fachada.cadastrarEvento("Cinema Lotado", "Filme novo", "Cultura", endereco, data, inicio, fim, 1, 15.0, joao);

        Evento evento = fachada.ListarEventos().stream()
                .filter(e -> e.getTitulo().equals("Cinema Lotado"))
                .findFirst()
                .orElseThrow();

        Ingresso ingresso = new com.sge.negocio.entidade.ingresso.IngressoInteiro(evento, evento.getValorBase());

        // Primeira compra deve ser bem-sucedida
        fachada.comprarIngresso(joao, evento, ingresso);

        // Segunda compra deve lançar exceção

        fachada.comprarIngresso(jurandir, evento, ingresso);

    }

}