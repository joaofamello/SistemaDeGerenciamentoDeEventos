package com.sge.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.sge.negocio.entidade.Usuario;

public class Menu extends Application {
    private Usuario usuarioLogado;
    private Scene menuScene;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        atualizarTitulo();
    }

    private void atualizarTitulo() {
        if (usuarioLogado != null && menuScene != null) {
            Stage stage = (Stage) menuScene.getWindow();
            if (stage != null) {
                stage.setTitle("SGE - Menu Principal (" + usuarioLogado.getNomeUsuario() + ")");
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Configuração da janela principal
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

        // Barra de menu estilizada
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #d7d7d7; -fx-text-fill: #000000;");

        // Menu Eventos
        javafx.scene.control.Menu menuEventos = new javafx.scene.control.Menu("Eventos");
        menuEventos.setStyle("-fx-text-fill: white;  -fx-text-color: white; -fx-font-weight: bold;");

        MenuItem criarEvento = new MenuItem("Criar Evento");
        criarEvento.setStyle("-fx-font-weight: bold;");
        MenuItem participarEvento = new MenuItem("Participar de Eventos");
        participarEvento.setStyle("-fx-font-weight: bold;");

        // Menu Usuário
        javafx.scene.control.Menu menuUsuario = new javafx.scene.control.Menu("Usuário");
        menuUsuario.setStyle("-fx-text-fill: white; -fx-text-color: white; -fx-font-weight: bold;");

        MenuItem logoutItem = new MenuItem("Sair");
        logoutItem.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        MenuItem MostrarEventos = new MenuItem("Gerenciar meus eventos");
        MostrarEventos.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        MenuItem EventosParticipado = new MenuItem("Eventos participando");
        EventosParticipado.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        // Adicionando ações
        criarEvento.setOnAction(e -> criarEvento());
        participarEvento.setOnAction(e -> participarEvento());
        logoutItem.setOnAction(e -> fazerLogout(primaryStage));
        MostrarEventos.setOnAction(e -> mostrarEventos());
        EventosParticipado.setOnAction(e -> eventosParticipado());

        // Montando os menus
        menuEventos.getItems().addAll(criarEvento, participarEvento);
        menuUsuario.getItems().addAll(MostrarEventos,EventosParticipado,logoutItem);
        menuBar.getMenus().addAll(menuEventos, menuUsuario);

        // Painel de boas-vindas
        Label welcomeLabel = new Label();
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.web("#3498db"));

        if (usuarioLogado != null) {
            welcomeLabel.setText("Bem-vindo, " + usuarioLogado.getNomeCompleto() + "!");
        } else {
            welcomeLabel.setText("Bem-vindo ao SGE!");
        }

        Label infoLabel = new Label("Selecione uma opção no menu superior");
        infoLabel.setFont(Font.font("Arial", 14));
        infoLabel.setTextFill(Color.GRAY);

        VBox centerBox = new VBox(20, welcomeLabel, infoLabel);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        // Layout principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        root.setTop(menuBar);
        root.setCenter(centerBox);

        // Configuração da cena
        menuScene = new Scene(root);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void fazerLogout(Stage primaryStage) {
        try {
            LoginCadastro loginApp = new LoginCadastro();
            loginApp.abrirTelaLogin();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível fazer logout: " + e.getMessage());
        }
    }

    private void criarEvento() {
        if (usuarioLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Nenhum usuário logado. Faça login novamente.");
            return;
        }

        Stage criarEventoStage = new Stage();
        CriarEvento criarEventoApp = new CriarEvento();
        criarEventoApp.setUsuarioLogado(usuarioLogado);
        criarEventoApp.start(criarEventoStage);
    }

    private void participarEvento() {
        if (usuarioLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Nenhum usuário logado. Faça login novamente.");
            return;
        }

        Stage participarEventoStage = new Stage();
        ParticiparEvento participarEventoApp = new ParticiparEvento();
        participarEventoApp.setUsuarioLogado(usuarioLogado);
        participarEventoApp.start(participarEventoStage);
    }

    private void mostrarEventos(){
        if (usuarioLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Nenhum usuário logado. Faça login novamente.");
            return;
        }

        Stage mostrarEventoStage = new Stage();
        MostrarEventos mostrareventosApp = new MostrarEventos();
        mostrareventosApp.setUsuarioLogado(usuarioLogado);
        mostrareventosApp.start(mostrarEventoStage);
    }

    private void eventosParticipado(){
        if (usuarioLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Nenhum usuário logado. Faça login novamente.");
            return;
        }
        Stage eventosParticipadoStage = new Stage();
        EventosParticipado eventosParticipadoApp = new EventosParticipado();
        eventosParticipadoApp.setUsuarioLogado(usuarioLogado);
        eventosParticipadoApp.start(eventosParticipadoStage);

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}