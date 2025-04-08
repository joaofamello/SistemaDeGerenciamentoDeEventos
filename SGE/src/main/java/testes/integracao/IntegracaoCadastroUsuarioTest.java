package testes.integracao;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.excecao.EmailJaExistenteException;

import java.util.List;

public class IntegracaoCadastroUsuarioTest {
    private SGE fachada;

    @BeforeEach
    public void setUp() {
        fachada = SGE.getInstancia();
    }

    @Test
    public void testCadastroEListagemDeUsuarios() throws FormularioUsuarioInvalidoException, EmailJaExistenteException {
        // Cadastrando um novo usuário
        fachada.cadastrarUsuario("João Silva", "joao123", "joao@email.com", "87123456789", "senha123");

        // Listando usuários
        List<Usuario> usuarios = fachada.ListarUsuarios();
        assertEquals(1, usuarios.size());
        assertEquals("João Silva", usuarios.get(0).getNomeCompleto());
    }
}