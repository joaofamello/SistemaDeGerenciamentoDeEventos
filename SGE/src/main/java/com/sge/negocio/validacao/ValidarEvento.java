package com.sge.negocio.validacao;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.sge.negocio.excecao.EventoDuplicadoException;

/**
 * Classe que valida se os dados passados pelo usuário estão de acordo com a norma do sistema.
 * @author Jurandir, Guilherme
 */
public class ValidarEvento {
    /**
     * Verifica tudo
     * @param titulo título do evento
     * @param descricao descrição do evento
     * @param endereco endereço que será sediado o evento
     * @param dataEvento data do evento
     * @param horaInicio hora em que o evento começa
     * @param horaFim hora que o evento termina
     * @param qtdeIngressos quantidade de ingressos para aquele evento
     * @param usuarioLogado usuário que está logado e operando o sistema
     * @throws FormularioEventoInvalidoException caso os parâmetros não estejam de acordo com as esperadas pelo sistema
     * @throws EventoDuplicadoException caso o evento já exista no repositório, evitando duplicações
     */
    public void validar(String titulo, String descricao,
                        Endereco endereco, LocalDate dataEvento, String categoria, LocalDateTime horaInicio, LocalDateTime horaFim,
                        int qtdeIngressos, double valorBase, Usuario usuarioLogado)
            throws FormularioEventoInvalidoException, EventoDuplicadoException {

        validarTitulo(titulo);
        validarDescricao(descricao);
        validarCategoria(categoria);
        validarEndereco(endereco);
        validarDatas(dataEvento, horaInicio, horaFim);
        validarIngressos(qtdeIngressos);
        validarValorIngresso(valorBase);
        validarAnfitriao(usuarioLogado);
    }

    /**
     * Valida o título para garantir que o parâmetro segue o padrão do sistema
     * @param titulo título do evento
     * @throws FormularioEventoInvalidoException caso o parâmetro não esteja de acordo
     */
    private void validarTitulo(String titulo) throws FormularioEventoInvalidoException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("titulo", "Título não pode ser vazio");
        }
        if (titulo.length() > 100) {
            throw new FormularioEventoInvalidoException("titulo", "Título deve ter no máximo 100 caracteres");
        }
    }

    /**
     * Valida a descrição do evento para garantir que esteja seguindo o padrão do sistema
     * @param descricao descrição do evento
     * @throws FormularioEventoInvalidoException caso o parâmetro não esteja de acordo
     */
    private void validarDescricao(String descricao) throws FormularioEventoInvalidoException {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("descricao", "Descrição não pode ser vazia");
        }
        if (descricao.length() > 500) {
            throw new FormularioEventoInvalidoException("descricao", "Descrição deve ter no máximo 500 caracteres");
        }
    }

    /**
     * Valida se a categoria foi preenchida corretamente
     * @param categoria recebe a categoria do evento
     * @throws FormularioEventoInvalidoException caso o parâmetro seja vazio
     */
    private void validarCategoria(String categoria) throws FormularioEventoInvalidoException {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new FormularioEventoInvalidoException("categoria", "Categoria não pode ser vazio");
        }

    }

    private void validarValorIngresso(double valorIngresso) throws FormularioEventoInvalidoException {
        if (valorIngresso <= 0) {
                throw new FormularioEventoInvalidoException("valorBase", "Valor do ingresso deve ser maior que zero.");
        }
    }


    /**
     * Verifica se o endereço segue o padrão estipulado pelo sistema
     * @param endereco endereço do evento
     * @throws FormularioEventoInvalidoException caso o parâmetro não esteja de acordo
     */
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

    /**
     * Verifica se a data passada como parâmetro segue o padrão do sistema
     * @param data data do evento
     * @param horaInicio horário do início do evento
     * @param horaFim horário em que o evento termina
     * @throws FormularioEventoInvalidoException caso os parâmetros não estejam de acordo
     */
    private void validarDatas(LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim)
            throws FormularioEventoInvalidoException {

        if (data == null || horaInicio == null || horaFim == null) {
            throw new FormularioEventoInvalidoException("data", "Data e horários devem ser preenchidos");
        }

        LocalDateTime inicioCompleto = horaInicio.with(data);
        LocalDateTime fimCompleto = horaFim.with(data);
        LocalDateTime agora = LocalDateTime.now();

        if (inicioCompleto.isBefore(agora)) {
            throw new FormularioEventoInvalidoException("horaInicio", "Data/hora deve ser futura");
        }
        if (fimCompleto.isBefore(inicioCompleto)) {
            throw new FormularioEventoInvalidoException("horaFim", "Hora de término deve ser após a hora de início");
        }
        if (fimCompleto.isAfter(inicioCompleto.plusHours(12))) {
            throw new FormularioEventoInvalidoException("horaFim", "Evento não pode durar mais que 12 horas");
        }
        if (inicioCompleto.equals(fimCompleto)) {
            throw new FormularioEventoInvalidoException("horaFim", "Hora de término deve ser diferente da hora de início");
        }
    }

    /**
     * Verifica se a quantidade de ingressos passada como parâmetro está de acordo com as regras do sistema
     * @param qtdeIngressos quantidade de ingressos para o evento
     * @throws FormularioEventoInvalidoException caso o parâmetro passado não esteja de acordo com o que se espera
     */
    private void validarIngressos(int qtdeIngressos) throws FormularioEventoInvalidoException {
        if (qtdeIngressos <= 0) {
            throw new FormularioEventoInvalidoException("qtdeIngressos", "Quantidade de ingressos deve ser positiva");
        }
        if (qtdeIngressos > 10000) {
            throw new FormularioEventoInvalidoException("qtdeIngressos", "Quantidade máxima é 10.000 ingressos");
        }
    }

    /**
     * Verifica se o usuário anfitrião está sendo passado da maneira correta para o sistema
     * @param anfitriao usuário anfitrião do evento
     * @throws FormularioEventoInvalidoException caso o parâmetro não esteja de acordo com as normas do sistema
      */
    private void validarAnfitriao(Usuario anfitriao) throws FormularioEventoInvalidoException {
        if (anfitriao == null) {
            throw new FormularioEventoInvalidoException("anfitriao", "Anfitrião não pode ser nulo");
        }
    }
}
