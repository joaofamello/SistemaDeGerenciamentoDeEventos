package testes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.validacao.ValidarUsuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;

class ValidarUsuarioTest {

    @Test
    public void testValidarUsuarioValido() {
        ValidarUsuario validador = new ValidarUsuario();
        Usuario usuario = new Usuario("João Silva", "joao123", "joao@email.com", "87999999999", "senha123");

        assertDoesNotThrow(() -> validador.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha()));
    }

    @Test
    public void testValidarUsuarioInvalido() {
        ValidarUsuario validador = new ValidarUsuario();
        Usuario usuario = new Usuario("", "joao123", "joao@email.com", "87123456789", "senha123");

        Exception exception = assertThrows(FormularioUsuarioInvalidoException.class, () -> {
            validador.validar(usuario.getNomeCompleto(), usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getSenha());
        });

        assertEquals("Nome completo obrigatório", exception.getMessage());
    }
}