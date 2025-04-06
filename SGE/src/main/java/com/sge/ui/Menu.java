package com.sge.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.sge.ui.*;
import com.sge.negocio.entidade.*;

public class Menu extends Application {
    private Usuario usuarioLogado;
    private Scene Menu;

    // Metodo para receber o usuário logado
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        atualizarTitulo();
    }

    private void atualizarTitulo() {
        if (usuarioLogado != null) {
            Stage stage = (Stage) Menu.getWindow();
            stage.setTitle("Menu de Eventos - Bem-vindo, " + usuarioLogado.getNomeUsuario());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Criação da barra de menu
        MenuBar menuBar = new MenuBar();

        // Menu "Eventos"
        javafx.scene.control.Menu menuEventos = new javafx.scene.control.Menu("Eventos");

        // Menu "Usuário"
        javafx.scene.control.Menu menuUsuario = new javafx.scene.control.Menu("Usuário");
        MenuItem logoutItem = new MenuItem("Sair");
        logoutItem.setOnAction(e -> fazerLogout(primaryStage));

        menuUsuario.getItems().add(logoutItem);

        // Itens do menu
        MenuItem criarEvento = new MenuItem("Criar Evento");
        MenuItem participarEvento = new MenuItem("Participar de Evento");

        // Adicionando ações aos itens do menu
        criarEvento.setOnAction(e -> criarEvento());
        participarEvento.setOnAction(e -> participarEvento());

        // Adicionando itens ao menu
        menuEventos.getItems().addAll(criarEvento, participarEvento);
        menuBar.getMenus().addAll(menuEventos, menuUsuario);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        // Configuração da cena
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Menu de Eventos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void fazerLogout(Stage primaryStage) {
        // Volta para a tela de login
        LoginCadastro loginApp = new LoginCadastro();
        loginApp.start(primaryStage);
    }

    // Metodo para criar evento
    private void criarEvento() {
        if (usuarioLogado == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum usuário logado. Faça login novamente.");
            alert.showAndWait();
            return;
        }

        Stage criarEventoStage = new Stage();
        CriarEvento criarEventoApp = new CriarEvento();

        // Passa o usuário logado para a tela de criar evento
        criarEventoApp.setUsuarioLogado(usuarioLogado);

        criarEventoApp.start(criarEventoStage);
    }

    // Metodo para participar de evento
    private void participarEvento() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Participar de Evento");
        alert.setHeaderText(null);
        alert.setContentText("Aqui você pode participar de um evento existente.");
        alert.showAndWait();
    }

    public Scene getMenu() {
        return Menu;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
