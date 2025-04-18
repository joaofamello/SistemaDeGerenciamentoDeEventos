package testes.unidade;
import static org.junit.jupiter.api.Assertions.*;

import com.sge.negocio.excecao.UsernameJaExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.negocio.NegocioUsuario;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.excecao.EmailJaExistenteException;
import com.sge.fachada.*;

class CadastrarUsuarioTest {
    private NegocioUsuario negocioUsuario;
    private SGE fachada = SGE.getInstancia();

    @BeforeEach
    public void setUp() {
        negocioUsuario = new NegocioUsuario(new RepositorioUsuariosArrayList());
    }

    @Test
    public void testCadastrarUsuario() throws FormularioUsuarioInvalidoException, EmailJaExistenteException {
        Usuario usuario = new Usuario("João Silva", "joao123", "joao@email.com", "87123456789", "senha123");
        negocioUsuario.inserir(usuario);

        assertEquals(1, negocioUsuario.listarTodosUsuarios().size());
    }

    @Test
    public void testNaoPermitirCadastroComEmailDuplicado() throws FormularioUsuarioInvalidoException, EmailJaExistenteException, UsernameJaExisteException {
        // Cadastra o primeiro usuário com um e-mail
        Usuario usuario1 = new Usuario("João Silva", "joao123", "joao5@email.com", "87123456789", "senha123");
        fachada.cadastrarUsuario(usuario1.getNomeCompleto(), usuario1.getNomeUsuario(), usuario1.getEmail(), usuario1.getTelefone(), usuario1.getSenha());

        // Tenta cadastrar outro com o mesmo e-mail
        Usuario usuario2 = new Usuario("Maria Souza", "maria123", "joao5@email.com", "87987654321", "senha456");


        fachada.existeSistemaUsers(usuario2.getEmail(), usuario2.getNomeUsuario());


    }
}