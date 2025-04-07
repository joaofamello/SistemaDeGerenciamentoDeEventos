package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.excecao.EventoDuplicadoException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import com.sge.negocio.validacao.ValidarEvento;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.sge.negocio.entidade.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CriarEvento extends Application {
    private ValidarEvento ValidarEvento = new ValidarEvento();
    private SGE fachada = SGE.getInstancia();
    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Criar Evento");

        Label tituloPrincipal = new Label("Cadastro de Evento");
        tituloPrincipal.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        tituloPrincipal.setPadding(new Insets(10, 0, 20, 0));

        Button btnLogout = new Button("Fechar");
        btnLogout.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogout.setOnAction(e -> primaryStage.close());

        HBox topBar = new HBox(btnLogout);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));

        TextField txtTitulo = new TextField();
        TextArea txtDescricao = new TextArea();
        TextField txtCategoria = new TextField();
        TextField txtEstado = new TextField();
        TextField txtCidade = new TextField();
        TextField txtRua = new TextField();
        TextField txtCep = new TextField();
        TextField txtBairro = new TextField();
        TextField txtNumero = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField txtHoraInicio = new TextField();
        TextField txtHoraFim = new TextField();
        Spinner<Integer> spinnerIngressos = new Spinner<>(1, 1000, 1);
        spinnerIngressos.setEditable(true);
        TextField txtValorBase = new TextField();

        Button btnCriarEvento = new Button("Criar Evento");
        btnCriarEvento.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCriarEvento.setOnAction(e -> {

            try {
                String titulo = txtTitulo.getText();
                String descricao = txtDescricao.getText();
                String categoria = txtCategoria.getText();
                Endereco endereco = new Endereco(
                        txtEstado.getText(),
                        txtCidade.getText(),
                        txtCep.getText(),
                        txtBairro.getText(),
                        txtRua.getText(),
                        Integer.parseInt(txtNumero.getText())
                );
                LocalDate dataEvento = datePicker.getValue();
                LocalDateTime horaInicio = LocalDateTime.parse(dataEvento + "T" + txtHoraInicio.getText());
                LocalDateTime horaFim = LocalDateTime.parse(dataEvento + "T" + txtHoraFim.getText());
                int qtdeIngressos = spinnerIngressos.getValue();
                double valorBase = Double.parseDouble(txtValorBase.getText());

                List<Evento> eventos = fachada.ListarEventos();
                if (fachada.existeEventoComTitulo(titulo)) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Já tem um evento com esse titulo registrado!");
                    error.showAndWait();

                }

                    fachada.validarConflitoPorEndereco(dataEvento, horaInicio, horaFim, endereco);
                    ValidarEvento.validar(titulo, descricao, categoria, endereco, dataEvento, horaInicio, horaFim, qtdeIngressos, valorBase, usuarioLogado);
                    fachada.cadastrarEvento(titulo, descricao, categoria, endereco, dataEvento, horaInicio, horaFim, qtdeIngressos, valorBase, usuarioLogado);


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Evento Criado");
                    alert.setHeaderText(null);
                    alert.setContentText("Evento '" + titulo + "' criado com sucesso!");
                    alert.showAndWait();


                    usuarioLogado.setEhAnfitriao(true);





            } catch (EventoDuplicadoException | FormularioEventoInvalidoException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(ex.getMessage());
                error.showAndWait();
            }
            fachada.SalvarArquivoEvento();

        });

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        int row = 0;
        grid.add(new Label("Título:"), 0, row); grid.add(txtTitulo, 1, row++);
        grid.add(new Label("Descrição:"), 0, row); grid.add(txtDescricao, 1, row++);
        grid.add(new Label("Categoria:"), 0, row); grid.add(txtCategoria, 1, row++);
        grid.add(new Label("Estado:"), 0, row); grid.add(txtEstado, 1, row++);
        grid.add(new Label("Cidade:"), 0, row); grid.add(txtCidade, 1, row++);
        grid.add(new Label("Rua:"), 0, row); grid.add(txtRua, 1, row++);
        grid.add(new Label("CEP:"), 0, row); grid.add(txtCep, 1, row++);
        grid.add(new Label("Bairro:"), 0, row); grid.add(txtBairro, 1, row++);
        grid.add(new Label("Número:"), 0, row); grid.add(txtNumero, 1, row++);
        grid.add(new Label("Data do Evento:"), 0, row); grid.add(datePicker, 1, row++);
        grid.add(new Label("Hora Início (HH:mm):"), 0, row); grid.add(txtHoraInicio, 1, row++);
        grid.add(new Label("Hora Fim (HH:mm):"), 0, row); grid.add(txtHoraFim, 1, row++);
        grid.add(new Label("Qtde Ingressos:"), 0, row); grid.add(spinnerIngressos, 1, row++);
        grid.add(new Label("Valor Base (R$):"), 0, row); grid.add(txtValorBase, 1, row++);
        grid.add(btnCriarEvento, 1, row);

        VBox layout = new VBox(10, topBar, tituloPrincipal, grid);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 650, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
