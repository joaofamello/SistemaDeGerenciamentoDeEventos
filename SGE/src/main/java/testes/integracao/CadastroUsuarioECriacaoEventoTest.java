package testes.integracao;
import static org.junit.jupiter.api.Assertions.*;

import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.excecao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CadastroUsuarioECriacaoEventoTest {
    private SGE fachada;

    @BeforeEach
    public void setUp() {
        fachada = SGE.getInstancia();
    }

    @Test
        public void CadastroUsuarioECriacaoEventoTest() throws FormularioUsuarioInvalidoException, FormularioEventoInvalidoException, EventoDuplicadoException, LoginFalhouException {

        // Cadastrar usuário
        fachada.cadastrarUsuario("Guilherme Teste", "guilhermeTest", "guilherme@test.com", "87912345678", "senha123");
        Usuario maria = fachada.LoginUsuario("guilhermeTest", "senha123");

        // Criar evento
        Endereco endereco = new Endereco("PE", "Garanhuns", "12345-000", "Centro", "Rua São Sebastião", 10);
        LocalDate data = LocalDate.now().plusDays(5);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(18, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(22, 0));

        fachada.cadastrarEvento("Festa Teste", "Aniversário", "Festa", endereco, data, inicio, fim, 50, 20.0, maria);

        // Verificar se o evento foi cadastrado
        List<Evento> eventos = fachada.ListarEventos();
        assertTrue(eventos.stream().anyMatch(e -> e.getTitulo().equals("Festa Teste")));
    }

    @Test
    public void CadastroUsuarioECriacaoEventoFalhaTest() throws FormularioUsuarioInvalidoException, FormularioEventoInvalidoException, EventoDuplicadoException, LoginFalhouException {

        // Cadastrar usuário
        fachada.cadastrarUsuario("Jurandir Teste", "JurandirTest", "Jura@test.com", "87912345678", "senha123");
        Usuario jurandir = fachada.LoginUsuario("Test", "senha123");

        // Criar evento
        Endereco endereco = new Endereco("PE", "Garanhuns", "12345-000", "Centro", "Rua São Sebastião", 10);
        LocalDate data = LocalDate.now().plusDays(5);
        LocalDateTime inicio = LocalDateTime.of(data, LocalTime.of(18, 0));
        LocalDateTime fim = LocalDateTime.of(data, LocalTime.of(22, 0));

        fachada.cadastrarEvento("Festa Teste", "Aniversário", "Festa", endereco, data, inicio, fim, 50, 20.0, jurandir);

        // Verificar se o evento foi cadastrado
        List<Evento> eventos = fachada.ListarEventos();
        assertTrue(eventos.stream().anyMatch(e -> e.getTitulo().equals("Festa Teste")));
    }

}