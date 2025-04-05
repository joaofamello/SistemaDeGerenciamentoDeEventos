package com.sge.negocio.validacao;

import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;

public class ValidarUsuario {
    private static final int tamanhoMinimoSenha= 6;
    private static final int tamanhoMaximoNomeUsuario = 20;

    public void validar(Usuario usuario) throws FormularioUsuarioInvalidoException {
        validarNomeCompleto(usuario.getNomeCompleto());
        validarNomeUsuario(usuario.getNomeUsuario());
        validarEmail(usuario.getEmail());
        validarTelefone(usuario.getTelefone());
        validarSenha(usuario.getSenha());
    }

    private void validarNomeCompleto(String nomeCompleto) throws FormularioUsuarioInvalidoException {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("nomeCompleto", "Nome completo obrigatório");
        }
        if (nomeCompleto.length() > 100) {
            throw new FormularioUsuarioInvalidoException("nomeCompleto", "Nome muito longo (máx. 100 caracteres)");
        }
    }

    private void validarNomeUsuario(String nomeUsuario) throws FormularioUsuarioInvalidoException {
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

    private void validarEmail(String email) throws FormularioUsuarioInvalidoException {
        if (email == null || email.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("email", "E-mail obrigatório");
        }
        // Verifica se tem pelo menos um caractere antes e depois do @
        String[] partes = email.split("@");
        if (partes.length != 2 || partes[0].isEmpty() || partes[1].isEmpty()) {
            throw new FormularioUsuarioInvalidoException("email", "Formato inválido (exemplo: usuario@dominio.com)");
        }
    }

    private void validarTelefone(String telefone) throws FormularioUsuarioInvalidoException {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new FormularioUsuarioInvalidoException("telefone", "Telefone obrigatório");
        }
        //Adicionando a variavel "telefoneLimpo" para remover qualquer caractere indesejado no telefone
        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
        if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
            throw new FormularioUsuarioInvalidoException("telefone", "Telefone inválido (DDD + número)");
        }
    }

    private void validarSenha(String senha) throws FormularioUsuarioInvalidoException {
        if (senha == null || senha.length() < tamanhoMinimoSenha) {
            throw new FormularioUsuarioInvalidoException("senha",
                    "Senha deve ter no mínimo " + tamanhoMinimoSenha + " caracteres");
        }
    }
}
