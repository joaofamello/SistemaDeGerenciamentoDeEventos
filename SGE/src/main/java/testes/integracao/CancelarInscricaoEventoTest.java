package testes.integracao;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.entidade.ingresso.IngressoInteiro;
import com.sge.negocio.excecao.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;

public class CancelarInscricaoEventoTest {
    private SGE fachada;

    @BeforeEach
    public void setUp() {
        fachada = SGE.getInstancia();
    }

    @Test
    public void CancelarInscricaoEventoTest() throws FormularioUsuarioInvalidoException, LoginFalhouException, IngressosEsgotadoException, ErroCancelarInscricaoException, FormularioEventoInvalidoException, EventoDuplicadoException {
        // Cadastro de usuário
        fachada.cadastrarUsuario("Lucas", "lucas123", "lucas@email.com", "87911112222", "senha123");
        Usuario lucas = fachada.LoginUsuario("lucas123", "senha123");

        // Criação de evento
        Endereco endereco = new Endereco("BA", "Salvador", "40000-000", "Barra", "Rua do Amor", 88);
        LocalDate data = LocalDate.now().plusDays(5);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(15, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(18, 0));

        fachada.cadastrarEvento("Palestra", "Palestra sobre Java", "Tecnologia", endereco, data, inicio, fim, 20, 25.0, lucas);
        Evento evento = fachada.ListarEventos().stream().filter(e -> e.getTitulo().equals("Palestra")).findFirst().orElseThrow();

        // Compra de ingresso
        fachada.comprarIngresso(lucas, evento, new IngressoInteiro(evento, evento.getValorBase()));

        // Garantir que o usuário está participando
        assertTrue(evento.getParticipantes().contains(lucas));
        assertTrue(lucas.getEventosParticipados().contains(evento));

        // Cancelar participação
        fachada.cancelarInscricao(lucas, evento);

        // Verificar se ele foi removido
        assertFalse(evento.getParticipantes().contains(lucas));
        assertFalse(lucas.getEventosParticipados().contains(evento));
    }

    @Test
    public void testCancelarSemParticipacaoFalha() throws ErroCancelarInscricaoException, FormularioEventoInvalidoException, EventoDuplicadoException, LoginFalhouException, FormularioUsuarioInvalidoException {
        // Cadastra o usuário
        fachada.cadastrarUsuario("Ana", "ana123", "ana@email.com", "87988889999", "senha123");
        Usuario ana = fachada.LoginUsuario("ana123", "senha123");

        // Cria o evento
        Endereco endereco = new Endereco("SP", "Campinas", "13000-000", "Centro", "Rua Azul", 101);
        LocalDate data = LocalDate.now().plusDays(2);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(14, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(18, 0));

        fachada.cadastrarEvento("Evento Restrito", "Sem participação", "Negócios", endereco, data, inicio, fim, 10, 50.0, ana);
        Evento evento = fachada.ListarEventos().stream().filter(e -> e.getTitulo().equals("Evento Restrito")).findFirst().orElseThrow();

        // Ana não participa do evento, mas tenta cancelar
        fachada.cancelarInscricao(ana, evento);

    }
}
