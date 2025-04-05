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

public class Cadastro extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tela de Cadastro");

        // Criando o layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Criando os componentes
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

        // Adicionando os componentes ao grid
        grid.add(nomeCompletoLabel, 0, 0);
        grid.add(nomeCompletoField, 1, 0);
        grid.add(usuarioLabel, 0, 1);
        grid.add(usuarioField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(telefoneLabel, 0, 3);
        grid.add(telefoneField, 1, 3);
        grid.add(senhaLabel, 0, 4);
        grid.add(senhaField, 1, 4);
        grid.add(cadastrarButton, 1, 5);

        // Ação do botão de cadastrar
        cadastrarButton.setOnAction(e -> {
            String nomeCompleto = nomeCompletoField.getText();
            String usuario = usuarioField.getText();
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String senha = senhaField.getText();

            // Aqui você pode adicionar a lógica para salvar os dados
            System.out.println("Nome Completo: " + nomeCompleto);
            System.out.println("Nome de Usuário: " + usuario);
            System.out.println("E-mail: " + email);
            System.out.println("Telefone: " + telefone);
            System.out.println("Senha: " + senha);
        });

        // Criando a cena e adicionando o grid
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}