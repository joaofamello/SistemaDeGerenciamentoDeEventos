package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Endereco;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import com.sge.negocio.validacao.ValidarEvento;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EditarEvento extends Application {
    private SGE fachada = SGE.getInstancia();
    private Usuario usuarioLogado;
    private Evento eventoSelecionado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void setEventoSelecionado(Evento evento) {
        this.eventoSelecionado = evento;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (eventoSelecionado == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Nenhum evento selecionado para edição.");
            return;
        }

        primaryStage.setTitle("Editar Evento");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        TextField txtTitulo = new TextField(eventoSelecionado.getTitulo());
        TextArea txtDescricao = new TextArea(eventoSelecionado.getDescricao());
        TextField txtCategoria = new TextField(eventoSelecionado.getCategoria());

        TextField txtEstado = new TextField(eventoSelecionado.getEndereco().getEstado());
        TextField txtCidade = new TextField(eventoSelecionado.getEndereco().getCidade());
        TextField txtCep = new TextField(eventoSelecionado.getEndereco().getCep());
        TextField txtBairro = new TextField(eventoSelecionado.getEndereco().getBairro());
        TextField txtRua = new TextField(eventoSelecionado.getEndereco().getRua());
        TextField txtNumero = new TextField(String.valueOf(eventoSelecionado.getEndereco().getNumero()));

        DatePicker datePicker = new DatePicker(eventoSelecionado.getData());
        TextField txtHoraInicio = new TextField(eventoSelecionado.getHoraInicio().toLocalTime().toString());
        TextField txtHoraFim = new TextField(eventoSelecionado.getHoraFim().toLocalTime().toString());

        Spinner<Integer> spinnerIngressos = new Spinner<>(1, 10000, eventoSelecionado.getQtdeIngressos());
        TextField txtValorBase = new TextField(String.valueOf(eventoSelecionado.getValorBase()));

        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setOnAction(e -> {
            try {
                String titulo = txtTitulo.getText();
                String descricao = txtDescricao.getText();
                String categoria = txtCategoria.getText();
                Endereco endereco = new Endereco(
                        txtEstado.getText(), txtCidade.getText(), txtCep.getText(),
                        txtBairro.getText(), txtRua.getText(), Integer.parseInt(txtNumero.getText())
                );
                LocalDate dataEvento = datePicker.getValue();
                LocalDateTime horaInicio = LocalDateTime.parse(dataEvento + "T" + txtHoraInicio.getText());
                LocalDateTime horaFim = LocalDateTime.parse(dataEvento + "T" + txtHoraFim.getText());
                int qtdeIngressos = spinnerIngressos.getValue();
                double valorBase = Double.parseDouble(txtValorBase.getText());
                List<Evento> eventos = fachada.ListarEventos();
                ValidarEvento validador = new ValidarEvento();
                validador.validar(titulo, descricao, categoria, endereco, dataEvento, horaInicio, horaFim, qtdeIngressos, valorBase, usuarioLogado);

                //Atualiza os dados do evento selecionado
                eventoSelecionado.setTitulo(titulo);
                eventoSelecionado.setDescricao(descricao);
                eventoSelecionado.setCategoria(categoria);
                eventoSelecionado.setEndereco(endereco);
                eventoSelecionado.setData(dataEvento);
                eventoSelecionado.setHoraInicio(horaInicio);
                eventoSelecionado.setHoraFim(horaFim);
                eventoSelecionado.setQtdeIngressos(qtdeIngressos);
                eventoSelecionado.setValorBase(valorBase);

                fachada.SalvarArquivoEvento();

                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Evento atualizado com sucesso!");
                primaryStage.close();

            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erro ao salvar", ex.getMessage());
            }
        });

        grid.add(new Label("Título:"), 0, 0); grid.add(txtTitulo, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1); grid.add(txtDescricao, 1, 1);
        grid.add(new Label("Categoria:"), 0, 2); grid.add(txtCategoria, 1, 2);
        grid.add(new Label("Estado:"), 0, 3); grid.add(txtEstado, 1, 3);
        grid.add(new Label("Cidade:"), 0, 4); grid.add(txtCidade, 1, 4);
        grid.add(new Label("CEP:"), 0, 5); grid.add(txtCep, 1, 5);
        grid.add(new Label("Bairro:"), 0, 6); grid.add(txtBairro, 1, 6);
        grid.add(new Label("Rua:"), 0, 7); grid.add(txtRua, 1, 7);
        grid.add(new Label("Número:"), 0, 8); grid.add(txtNumero, 1, 8);
        grid.add(new Label("Data:"), 0, 9); grid.add(datePicker, 1, 9);
        grid.add(new Label("Hora Início:"), 0, 10); grid.add(txtHoraInicio, 1, 10);
        grid.add(new Label("Hora Fim:"), 0, 11); grid.add(txtHoraFim, 1, 11);
        grid.add(new Label("Qtde Ingressos:"), 0, 12); grid.add(spinnerIngressos, 1, 12);
        grid.add(new Label("Valor Base:"), 0, 13); grid.add(txtValorBase, 1, 13);
        grid.add(btnSalvar, 1, 14);

        Scene scene = new Scene(grid, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
