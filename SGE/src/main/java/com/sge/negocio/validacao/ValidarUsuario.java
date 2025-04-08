package com.sge.negocio.validacao;

import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;

/**
 * Classe responsável pelas validações do usuário
 *
 * Contém tamanhoMinimoSenha e tamanhoMaximoNomeUsuario.
 * @author Jurandir
 */
public class ValidarUsuario {
    private static final int tamanhoMinimoSenha= 6;
    private static final int tamanhoMaximoNomeUsuario = 20;

    /**
     * Método de validação geral
     *
     * @param nomeCompleto nome completo do usuário
     * @param nomeUsuario username do usuário
     * @param email e-mail do usuário
     * @param telefone telefone do usuário
     * @param senha senha do usuário
     * @throws FormularioUsuarioInvalidoException caso os parâmetros não estejam de acordo com as normas do sistema
     */
    public void validar(String nomeCompleto, String nomeUsuario, String email, String telefone, String senha) throws FormularioUsuarioInvalidoException{
        validarNomeCompleto(nomeCompleto);
        validarNomeUsuario(nomeUsuario);
        validarEmail(email);
        validarTelefone(telefone);
        validarSenha(senha);
    }

    /**
     * Verifica se o nome completo do usuário passado como parâmetro segue os padrões estipulados pelo sistema
     * @param nomeCompleto nome completo do usuário
     * @throws FormularioUsuarioInvalidoException caso o parâmetro não esteja no padrão esperado
     */
    public void validarNomeCompleto(String nomeCompleto) throws FormularioUsuarioInvalidoException {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("nomeCompleto", "Nome completo obrigatório");
        }
        if (nomeCompleto.length() > 100) {
            throw new FormularioUsuarioInvalidoException("nomeCompleto", "Nome muito longo (máx. 100 caracteres)");
        }
    }

    /**
     * Verifica se o username do usuário está de acordo com as regras do sistema
     * @param nomeUsuario username do usuário
     * @throws FormularioUsuarioInvalidoException caso o parâmetro não esteja de acordo
     */
    public void validarNomeUsuario(String nomeUsuario) throws FormularioUsuarioInvalidoException {
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("nomeUsuario", "Nome de usuário obrigatório");
        }
        if (nomeUsuario.length() > tamanhoMaximoNomeUsuario) {
            throw new FormularioUsuarioInvalidoException("nomeUsuario",
                    "Nome de usuário muito longo (máx. " + tamanhoMaximoNomeUsuario + " caracteres)");
        }
        if (!nomeUsuario.matches("[a-zA-Z0-9._-]+")) {
            throw new FormularioUsuarioInvalidoException("nomeUsuario",
                    "Use apenas letras, números, pontos, hífens ou underscores");
        }
    }

    /**
     * Verifica se o e-mail segue as normas esperadas pelo sistema
     * @param email e-mail do usuário
     * @throws FormularioUsuarioInvalidoException caso o parâmetro não esteja de acordo
     */
    public void validarEmail(String email) throws FormularioUsuarioInvalidoException{
        if (email == null || email.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("email", "E-mail obrigatório");
        }
        // Verifica se tem pelo menos um caractere antes e depois do @
        String[] partes = email.split("@");
        if (partes.length != 2 || partes[0].isEmpty() || partes[1].isEmpty()) {
            throw new FormularioUsuarioInvalidoException("email", "Formato inválido (exemplo: usuario@dominio.com)");
        }
        //fachada.existeEmail(email);

    }

    /**
     * Verifica se o telefone passado está de acordo com o esperado pelo sistema
     * @param telefone telefone do usuário
     * @throws FormularioUsuarioInvalidoException caso o parâmetro não esteja de acordo com o esperado
     */
    public void validarTelefone(String telefone) throws FormularioUsuarioInvalidoException {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("telefone", "Telefone obrigatório");
        }
        //Adicionando a variavel "telefoneLimpo" para remover qualquer caractere indesejado no telefone
        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
        if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
            throw new FormularioUsuarioInvalidoException("telefone", "Telefone inválido (DDD + número)");
        }
    }

    /**
     * Verifica se a senha está de acordo com o esperado pelo sistema
     * @param senha senha de acesso do usuário
     * @throws FormularioUsuarioInvalidoException caso o parâmetro passado não esteja de acordo com o esperado pelo sistema
     */
    public void validarSenha(String senha) throws FormularioUsuarioInvalidoException {
        if (senha == null || senha.length() < tamanhoMinimoSenha) {
            throw new FormularioUsuarioInvalidoException("senha",
                    "Senha deve ter no mínimo " + tamanhoMinimoSenha + " caracteres");
        }
    }
}
