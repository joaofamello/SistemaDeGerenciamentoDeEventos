package com.sge.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import com.sge.fachada.SGE;

public class Telas extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Aplicação com Múltiplas Telas");

        // Exibir a tela de login inicialmente
        mostrarTelaLogin();
    }

    private void mostrarTelaLogin() {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(20, 20, 20, 20));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);

        Label userNameLabel = new Label("Usuário:");
        TextField userNameField = new TextField();
        Label passwordLabel = new Label("Senha:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button cadastroButton = new Button("Cadastrar");

        loginGrid.add(userNameLabel, 0, 0);
        loginGrid.add(userNameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);
        loginGrid.add(loginButton, 1, 2);
        loginGrid.add(cadastroButton, 1, 3);

        // Ação do botão de login
        loginButton.setOnAction(e -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            System.out.println("Usuário: " + username + ", Senha: " + password);
        });

        // Ação do botão de cadastro
        cadastroButton.setOnAction(e -> mostrarTelaCadastro());

        Scene loginScene = new Scene(loginGrid, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void mostrarTelaCadastro() {
        GridPane cadastroGrid = new GridPane();
        cadastroGrid.setPadding(new Insets(20, 20, 20, 20));
        cadastroGrid.setVgap(10);
        cadastroGrid.setHgap(10);

        Label nomeCompletoLabel = new Label("Nome Completo:");
        TextField nomeCompletoField = new TextField();
        Label usuarioLabel = new Label("Nome de Usuário:");
        TextField usuarioField = new TextField();
        Label emailLabel = new Label("E-mail:");
        TextField emailField = new TextField();
        Label telefoneLabel = new Label("Telefone:");
        TextField telefoneField = new TextField();
        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();
        Button cadastrarButton = new Button("Cadastrar");
        Button voltarButton = new Button("Voltar");

        cadastroGrid.add(nomeCompletoLabel, 0, 0);
        cadastroGrid.add(nomeCompletoField, 1, 0);
        cadastroGrid.add(usuarioLabel, 0, 1);
        cadastroGrid.add(usuarioField, 1, 1);
        cadastroGrid.add(emailLabel, 0, 2);
        cadastroGrid.add(emailField, 1, 2);
        cadastroGrid.add(telefoneLabel, 0, 3);
        cadastroGrid.add(telefoneField, 1, 3);
        cadastroGrid.add(senhaLabel, 0, 4);
        cadastroGrid.add(senhaField, 1, 4);
        cadastroGrid.add(cadastrarButton, 1, 5);
        cadastroGrid.add(voltarButton, 1, 6);

        // Ação do botão de cadastrar
        cadastrarButton.setOnAction(e -> {
            String nomeCompleto = nomeCompletoField.getText();
            String usuario = usuarioField.getText();
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String senha = senhaField.getText();
            System.out.println("Nome Completo: " + nomeCompleto);
            System.out.println("Nome de Usuário: " + usuario);
            System.out.println("E-mail: " + email);
            System.out.println("Telefone: " + telefone);
            System.out.println("Senha: " + senha);
        });
    }
}