package com.sge.ui;

import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.EmailJaExistenteException;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import com.sge.negocio.excecao.LoginFalhouException;
import com.sge.negocio.excecao.UsernameJaExisteException;
import com.sge.negocio.validacao.ValidarUsuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.sge.fachada.SGE;
import com.sge.ui.*;

import java.util.List;

public class LoginCadastro extends Application {
    private SGE fachada = SGE.getInstancia();
    private ValidarUsuario ValidarU = new ValidarUsuario();
    private Scene loginScene; // Variável de instância para a cena de login


    @Override
    public void start(Stage primaryStage) {
        fachada.CarregarArquivos();
        primaryStage.setTitle("Login");

        // Criando o GridPane para a tela de login
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10, 10, 10));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

        // Criando os Labels e TextFields para login
        Label usuarioLabel = new Label("Nome de Usuário:");
        TextField usuarioField = new TextField();
        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();
        Button loginButton = new Button("Login");
        Button cadastrarButton = new Button("Cadastrar");

        // Adicionando os elementos ao GridPane de login
        loginGrid.add(usuarioLabel, 0, 0);
        loginGrid.add(usuarioField, 1, 0);
        loginGrid.add(senhaLabel, 0, 1);
        loginGrid.add(senhaField, 1, 1);
        loginGrid.add(loginButton, 0, 2);
        loginGrid.add(cadastrarButton, 1, 2);

        // Configurando a cena de login
        loginScene = new Scene(loginGrid, 300, 200); // Atribuindo à variável de instância
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Ação do botão de login
        loginButton.setOnAction(e -> {
            String nome = usuarioField.getText();
            String senha = senhaField.getText();

            try {
                Usuario usuario = fachada.LoginUsuario(nome, senha);
                abrirTelaMenu(primaryStage, usuario);
            } catch (LoginFalhouException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(ex.getMessage());
                error.showAndWait();
            }

        });

        // Chama o metodo para abrir a tela de cadastro
        cadastrarButton.setOnAction(e -> {
            abrirTelaCadastro(primaryStage);
        });
    }

    private void abrirTelaCadastro(Stage primaryStage) {
        // Criando o GridPane para a tela de cadastro
        GridPane cadastroGrid = new GridPane();
        cadastroGrid.setPadding(new Insets(10, 10, 10, 10));
        cadastroGrid.setVgap(8);
        cadastroGrid.setHgap(10);

        // Criando os Labels e TextFields para cadastro
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

        // Adicionando os elementos ao GridPane de cadastro
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
        cadastroGrid.add(cadastrarButton, 0, 5);
        cadastroGrid.add(voltarButton, 1, 5);

        // Configurando a cena de cadastro
        Scene cadastroScene = new Scene(cadastroGrid, 400, 300);
        primaryStage.setScene(cadastroScene);

        // Ação do botão de cadastrar na tela de cadastro
        cadastrarButton.setOnAction(e -> {
            try {
                String nomeCompleto = nomeCompletoField.getText();
                String nomeUsuario = usuarioField.getText();
                String email = emailField.getText();
                String telefone = telefoneField.getText();
                String Senha = senhaField.getText();

                ValidarU.validar(nomeCompleto, nomeUsuario, email, telefone, Senha);
                fachada.existeSistema(email, nomeUsuario);
                // Chama o metodo de cadastro com os valores extraídos
                fachada.cadastrarUsuario(
                        nomeCompleto,
                        nomeUsuario,
                        email,
                        telefone,
                        Senha
                );

                // Lista todos os usuários para verificação
                List<Usuario> usuarios = fachada.ListarUsuarios();
                System.out.println("Usuários cadastrados:");
                for (Usuario u : usuarios) {
                    System.out.println(u.getNomeUsuario() + " - " + u.getEmail() + " - " + u.getID());
                }

            } catch (FormularioUsuarioInvalidoException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(ex.getMessage());
                error.showAndWait();

            } catch (EmailJaExistenteException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(ex.getMessage());
                error.showAndWait();
            } catch (UsernameJaExisteException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(ex.getMessage());
                error.showAndWait();
            }
            fachada.SalvarArquivoUsuario();
        });

        // Volta para a tela de login
        voltarButton.setOnAction(e -> {
            primaryStage.setScene(loginScene);
        });
    }

    // Novo metodo para abrir a tela de menu
    private void abrirTelaMenu(Stage primaryStage, Usuario usuario) {
        Menu menuApp = new Menu();
        try {
            menuApp.start(primaryStage); // Reutiliza o mesmo Stage
            // Se quiser passar o usuário para o menu:
         menuApp.setUsuarioLogado(usuario); // Você precisará criar este metodo na classe Menu
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

