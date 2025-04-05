package com.sge.negocio.validacao;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ValidarEvento {

    public void validar(Evento evento) throws FormularioEventoInvalidoException {
        validarTitulo(evento.getTitulo());
        validarDescricao(evento.getDescricao());
        validarEndereco(evento.getEndereco());
        validarDatas(evento.getData(), evento.getHoraInicio(), evento.getHoraFim());
        validarIngressos(evento.getQtdeIngressos());
        validarAnfitriao(evento.getAnfitriao());
    }

    private void validarTitulo(String titulo) throws FormularioEventoInvalidoException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("titulo", "Título não pode ser vazio");
        }
        if (titulo.length() > 100) {
            throw new FormularioEventoInvalidoException("titulo", "Título deve ter no máximo 100 caracteres");
        }
    }

    private void validarDescricao(String descricao) throws FormularioEventoInvalidoException {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("descricao", "Descrição não pode ser vazia");
        }
        if (descricao.length() > 500) {
            throw new FormularioEventoInvalidoException("descricao", "Descrição deve ter no máximo 500 caracteres");
        }
    }

    private void validarEndereco(Endereco endereco) throws FormularioEventoInvalidoException {
        if (endereco == null) {
            throw new FormularioEventoInvalidoException("endereco", "Endereço completo é obrigatório");
        }

        if (endereco.getEstado() == null || endereco.getEstado().trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("estado", "Estado é obrigatório");
        }

        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("cidade", "Cidade é obrigatória");
        }

        if (endereco.getCep() == null || !endereco.getCep().matches("\\d{5}-?\\d{3}")) {
            throw new FormularioEventoInvalidoException("cep", "CEP inválido (formato esperado: XXXXX-XXX)");
        }

        if (endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("bairro", "Bairro é obrigatório");
        }

        if (endereco.getRua() == null || endereco.getRua().trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("rua", "Rua é obrigatória");
        }

        if (endereco.getNumero() <= 0) {
            throw new FormularioEventoInvalidoException("numero", "Número deve ser positivo");
        }
    }

    private void validarDatas(LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim)
            throws FormularioEventoInvalidoException {

        if (data == null || horaInicio == null || horaFim == null) {
            throw new FormularioEventoInvalidoException("data", "Data e horários devem ser preenchidos");
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioCompleto = LocalDateTime.of(data, horaInicio.toLocalTime());
        LocalDateTime fimCompleto = LocalDateTime.of(data, horaFim.toLocalTime());

        if (inicioCompleto.isBefore(agora)) {
            throw new FormularioEventoInvalidoException("data", "Data/hora deve ser futura");
        }
        if (fimCompleto.isBefore(inicioCompleto)) {
            throw new FormularioEventoInvalidoException("horaFim", "Hora de término deve ser após a hora de início");
        }
        if (fimCompleto.isAfter(inicioCompleto.plusHours(12))) {
            throw new FormularioEventoInvalidoException("horaFim", "Evento não pode durar mais que 12 horas");
        }
    }

    private void validarIngressos(int qtdeIngressos) throws FormularioEventoInvalidoException {
        if (qtdeIngressos <= 0) {
            throw new FormularioEventoInvalidoException("qtdeIngressos", "Quantidade de ingressos deve ser positiva");
        }
        if (qtdeIngressos > 10000) {
            throw new FormularioEventoInvalidoException("qtdeIngressos", "Quantidade máxima é 10.000 ingressos");
        }
    }

    private void validarAnfitriao(Usuario anfitriao) throws FormularioEventoInvalidoException {
        if (anfitriao == null) {
            throw new FormularioEventoInvalidoException("anfitriao", "Anfitrião não pode ser nulo");
        }
    }
}
