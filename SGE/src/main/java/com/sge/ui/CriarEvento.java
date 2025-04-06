package com.sge.ui;


import com.sge.fachada.SGE;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import com.sge.negocio.validacao.ValidarEvento;
import com.sge.negocio.validacao.ValidarUsuario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.sge.negocio.entidade.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.sge.ui.*;


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

        // Criando os campos do formulário
        TextField txtTitulo = new TextField();
        TextArea txtDescricao = new TextArea();
        TextField txtCategoria = new TextField();

        // Campos de endereço
        TextField txtEstado = new TextField();
        TextField txtCidade = new TextField();
        TextField txtRua = new TextField();
        TextField txtCep = new TextField();
        TextField txtBairro = new TextField();
        TextField txtNumero = new TextField();

        // Campos de data e hora
        DatePicker datePicker = new DatePicker();
        TextField txtHoraInicio = new TextField();
        TextField txtHoraFim = new TextField();

        // Campo para quantidade de ingressos
        Spinner<Integer> spinnerIngressos = new Spinner<>(1, 1000, 1);
        spinnerIngressos.setEditable(true); // Permite edição manual

        // Campo para valor base
        TextField txtValorBase = new TextField();

        // Botão para criar evento
        Button btnCriarEvento = new Button("Criar Evento");
        btnCriarEvento.setOnAction(e -> {
            try {
                //Coletar dados do formulário
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

                ValidarEvento.validar(titulo, descricao, categoria,
                        endereco, dataEvento, horaInicio, horaFim,
                        qtdeIngressos, valorBase, usuarioLogado);
                fachada.cadastrarEvento(titulo, descricao, categoria,
                        endereco, dataEvento, horaInicio, horaFim,
                        qtdeIngressos, valorBase, usuarioLogado);
                ValidarEvento.validarEventoUnicoNoDia(dataEvento, eventos);

                //Feedback ao usuário
                eventos = fachada.ListarEventos();
                System.out.println("Eventos cadastrados:");
                for (Evento ev : eventos) {
                    System.out.println(ev.getID() + "-" + ev.getTitulo() + " - " + ev.getDescricao() + " - " + ev.getID() + " - " + usuarioLogado.getNomeUsuario());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Evento Criado");
                alert.setHeaderText(null);
                alert.setContentText("Evento '" + titulo + "' criado com sucesso!");
                alert.showAndWait();
            } catch (FormularioEventoInvalidoException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(ex.getMessage());
                error.showAndWait();
            }
            fachada.SalvarArquivoEvento();
        });

        // Layout do formulário
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(new Label("Título:"), 1, 0);
        grid.add(txtTitulo, 2, 0);
        grid.add(new Label("Descrição:"), 1, 1);
        grid.add(txtDescricao, 2, 1);
        grid.add(new Label("Categoria:"), 1, 2);
        grid.add(txtCategoria, 2, 2);
        grid.add(new Label("Estado:"), 1, 3);
        grid.add(txtEstado, 2, 3);
        grid.add(new Label("Cidade:"), 1, 4);
        grid.add(txtCidade, 2, 4);
        grid.add(new Label("Rua:"), 1, 5);
        grid.add(txtRua, 2, 5);
        grid.add(new Label("CEP:"), 1, 6);
        grid.add(txtCep, 2, 6);
        grid.add(new Label("Bairro:"), 1, 7);
        grid.add(txtBairro, 2, 7);
        grid.add(new Label("Número:"), 1, 8);
        grid.add(txtNumero, 2, 8);
        grid.add(new Label("Data do Evento:"), 1, 9);
        grid.add(datePicker, 2, 9);
        grid.add(new Label("Hora Início:"), 1, 10);
        grid.add(txtHoraInicio, 2, 10);
        grid.add(new Label("Hora Fim:"), 1, 11);
        grid.add(txtHoraFim, 2, 11);
        grid.add(new Label("Qtde Ingressos:"), 1, 12);
        grid.add(spinnerIngressos, 2, 12);
        grid.add(new Label("Valor Base:"), 1, 13);
        grid.add(txtValorBase, 2, 13);
        grid.add(btnCriarEvento, 2, 14);

        // Configuração do layout principal
        VBox vbox = new VBox(grid);
        Scene scene = new Scene(vbox, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Criar Evento");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}