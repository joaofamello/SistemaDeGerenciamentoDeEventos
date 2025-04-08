package com.sge.ui;

import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.*;
import com.sge.negocio.validacao.ValidarUsuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.sge.fachada.SGE;

import java.util.List;

public class LoginCadastro extends Application {
    private SGE fachada = SGE.getInstancia();
    private ValidarUsuario ValidarU = new ValidarUsuario();
    private Scene loginScene;

    @Override
    public void start(Stage primaryStage) {
        fachada.CarregarArquivos();

        List<Evento> eventos = fachada.ListarEventos();
        System.out.println("Eventos cadastrados:");
        for (Evento ev : eventos) {
            System.out.println(ev.getID() + "-" + ev.getTitulo() + " - " + ev.getDescricao() + " - " + ev.getID() + " - ");
        }

        primaryStage.setTitle("Sistema de Gerenciamento de Eventos");

        //Tela de Login
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setStyle("-fx-background-color: #f5f5f5;");

        // Título
        Label tituloLabel = new Label("Login");
        tituloLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        tituloLabel.setTextFill(Color.DARKBLUE);
        GridPane.setColumnSpan(tituloLabel, 2);
        loginGrid.add(tituloLabel, 0, 0);

        // Campos de Login
        Label usuarioLabel = new Label("Usuário:");
        usuarioLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Digite seu nome de usuário");

        Label senhaLabel = new Label("Senha:");
        senhaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Digite sua senha");

        // Botões
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button loginButton = new Button("Entrar");
        loginButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");
        Button cadastrarButton = new Button("Cadastrar");
        cadastrarButton.setStyle("-fx-base: #2196F3; -fx-text-fill: white;");
        buttonBox.getChildren().addAll(loginButton, cadastrarButton);

        // Adicionando elementos ao grid
        loginGrid.add(usuarioLabel, 0, 1);
        loginGrid.add(usuarioField, 1, 1);
        loginGrid.add(senhaLabel, 0, 2);
        loginGrid.add(senhaField, 1, 2);
        loginGrid.add(buttonBox, 0, 3, 2, 1);

        loginScene = new Scene(loginGrid, 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        //Tela de cadastro
        GridPane cadastroGrid = new GridPane();
        cadastroGrid.setAlignment(Pos.CENTER);
        cadastroGrid.setPadding(new Insets(25, 25, 25, 25));
        cadastroGrid.setHgap(10);
        cadastroGrid.setVgap(10);
        cadastroGrid.setStyle("-fx-background-color: #f5f5f5;");

        // Título
        Label cadastroTitulo = new Label("Cadastro de Usuário");
        cadastroTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        cadastroTitulo.setTextFill(Color.DARKBLUE);
        GridPane.setColumnSpan(cadastroTitulo, 2);
        cadastroGrid.add(cadastroTitulo, 0, 0);

        // Campos de Cadastro
        addFormField(cadastroGrid, "Nome Completo:", new TextField(), 1);
        addFormField(cadastroGrid, "Nome de Usuário:", new TextField(), 2);
        addFormField(cadastroGrid, "E-mail:", new TextField(), 3);
        addFormField(cadastroGrid, "Telefone:", new TextField(), 4);
        addFormField(cadastroGrid, "Senha:", new PasswordField(), 5);

        // Botões
        HBox cadastroButtonBox = new HBox(10);
        cadastroButtonBox.setAlignment(Pos.CENTER);
        Button confirmarCadastroButton = new Button("Confirmar");
        confirmarCadastroButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");
        Button voltarButton = new Button("Voltar");
        voltarButton.setStyle("-fx-base: #f44336; -fx-text-fill: white;");
        cadastroButtonBox.getChildren().addAll(confirmarCadastroButton, voltarButton);
        cadastroGrid.add(cadastroButtonBox, 0, 6, 2, 1);

        Scene cadastroScene = new Scene(cadastroGrid, 500, 400);


        // Ação do botão de login
        loginButton.setOnAction(e -> {
            String nome = usuarioField.getText();
            String senha = senhaField.getText();

            try {
                Usuario usuario = fachada.LoginUsuario(nome, senha);
                abrirTelaMenu(primaryStage, usuario);
            } catch (LoginFalhouException ex) {
                showErrorAlert(ex.getMessage());
            }
        });

        // Ação do botão de cadastro
        cadastrarButton.setOnAction(e -> {
            primaryStage.setScene(cadastroScene);
        });

        // Ação do botão de confirmar cadastro
        confirmarCadastroButton.setOnAction(e -> {
            try {
                TextField nomeCompletoField = (TextField) getNodeFromGrid(cadastroGrid, 1, 1);
                TextField usuarioFieldCad = (TextField) getNodeFromGrid(cadastroGrid, 1, 2);
                TextField emailField = (TextField) getNodeFromGrid(cadastroGrid, 1, 3);
                TextField telefoneField = (TextField) getNodeFromGrid(cadastroGrid, 1, 4);
                PasswordField senhaFieldCad = (PasswordField) getNodeFromGrid(cadastroGrid, 1, 5);

                ValidarU.validar(
                        nomeCompletoField.getText(),
                        usuarioFieldCad.getText(),
                        emailField.getText(),
                        telefoneField.getText(),
                        senhaFieldCad.getText()
                );

                fachada.existeSistemaUsers(emailField.getText(), usuarioFieldCad.getText());
                fachada.cadastrarUsuario(
                        nomeCompletoField.getText(),
                        usuarioFieldCad.getText(),
                        emailField.getText(),
                        telefoneField.getText(),
                        senhaFieldCad.getText()
                );

                // Limpa os campos após cadastro
                nomeCompletoField.clear();
                usuarioFieldCad.clear();
                emailField.clear();
                telefoneField.clear();
                senhaFieldCad.clear();

                showSuccessAlert("Cadastro realizado com sucesso!");

            } catch (FormularioUsuarioInvalidoException |
                     EmailJaExistenteException |
                     UsernameJaExisteException ex) {
                showErrorAlert(ex.getMessage());
            }
            fachada.SalvarArquivoUsuario();
        });

        // Ação do botão voltar
        voltarButton.setOnAction(e -> {
            primaryStage.setScene(loginScene);
        });
    }

    //metodo auxiliares
    private void addFormField(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }

    private Node getNodeFromGrid(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void abrirTelaMenu(Stage primaryStage, Usuario usuario) {
        Menu menuApp = new Menu();
        try {
            menuApp.start(primaryStage);
            menuApp.setUsuarioLogado(usuario);
        } catch (Exception e) {
            showErrorAlert("Erro ao abrir o menu: " + e.getMessage());
        }
    }

    public void abrirTelaLogin() {
        Stage stage = new Stage();
        start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}