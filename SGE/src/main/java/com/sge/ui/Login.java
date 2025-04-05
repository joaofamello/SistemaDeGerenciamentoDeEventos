package com.sge.ui;

import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.validacao.ValidarUsuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login extends Application {
    private ValidarUsuario v = new ValidarUsuario();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tela de Login");

        // Criando o layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Criando os componentes
        Label userNameLabel = new Label("Usuário:");
        TextField userNameField = new TextField();
        Label passwordLabel = new Label("Senha:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        // Adicionando os componentes ao grid
        grid.add(userNameLabel, 0, 0);
        grid.add(userNameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Ação do botão de login
        loginButton.setOnAction(e -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            // Aqui você pode adicionar a lógica de autenticação
            try {
                v.validarNomeUsuario(username);
                v.validarSenha(password);
            } catch (FormularioUsuarioInvalidoException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Criando a cena e adicionando o grid
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}