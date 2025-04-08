package testes.integracao;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.EventoDuplicadoException;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.excecao.LoginFalhouException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CriacaoEventoTest {
    private SGE fachada;

    @BeforeEach
    public void setUp() {
        fachada = SGE.getInstancia();
    }

    @Test
    public void CriacaoEventoTest() throws FormularioUsuarioInvalidoException, LoginFalhouException,
            FormularioEventoInvalidoException, EventoDuplicadoException {

        // 1. Cadastra um usuário válido
        fachada.cadastrarUsuario("Gustavo", "Guga", "gost@email.com", "87988887777", "senha123");
        Usuario gustavo = fachada.LoginUsuario("Guga", "senha123");

        // 2. Cria um evento com dados válidos
        Endereco endereco = new Endereco("PE", "Recife", "50000-000", "Boa Viagem", "Av. Boa Viagem", 100);
        LocalDate dataFutura = LocalDate.now().plusDays(10);  // Data futura (válida)
        LocalDateTime inicio = LocalDateTime.of(dataFutura, LocalTime.of(19, 0));
        LocalDateTime fim = LocalDateTime.of(dataFutura, LocalTime.of(23, 0));

        // 3. Cadastra o evento (não deve lançar exceção)
        fachada.cadastrarEvento(
                "Show da Banda Hungria",
                "Show de Hip Hop",
                "Música",
                endereco,
                dataFutura,
                inicio,
                fim,
                100,  // Capacidade válida
                50.0, // Preço válido
                gustavo
        );

        // 4. Verifica se o evento foi criado corretamente
        List<Evento> eventos = fachada.ListarEventos();
        boolean eventoFoiCriado = eventos.stream()
                .anyMatch(e -> e.getTitulo().equals("Show da Banda Hungria"));

        assertTrue(eventoFoiCriado, "O evento deveria estar na lista de eventos cadastrados");
    }

    @Test
    public void CriacaoEventoFalhaTest() throws FormularioUsuarioInvalidoException, FormularioEventoInvalidoException, EventoDuplicadoException, LoginFalhouException {

        // Cadastrar usuário
        fachada.cadastrarUsuario("Jurandir Teste", "JurandirTest", "Jura@test.com", "87912345678", "senha123");
        Usuario jurandir = fachada.LoginUsuario("JurandirTest", "senha123");

        // Criar evento
        Endereco endereco = new Endereco("PE", "Garanhuns", "12345-000", "Centro", "Rua São Sebastião", 10);
        LocalDate data = LocalDate.now().plusDays(5);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(22, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(22, 0));

        fachada.cadastrarEvento("Festa Teste", "Aniversário", "Festa", endereco, data, inicio, fim, 50, 20.0, jurandir);

        // Verificar se o evento foi cadastrado
        List<Evento> eventos = fachada.ListarEventos();
        assertTrue(eventos.stream().anyMatch(e -> e.getTitulo().equals("Festa Teste")));
    }

}
