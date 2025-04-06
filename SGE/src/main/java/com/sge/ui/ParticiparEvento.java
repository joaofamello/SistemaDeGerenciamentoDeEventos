package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import com.sge.negocio.entidade.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ParticiparEvento extends Application {
    private SGE fachada = SGE.getInstancia();
    private Usuario usuarioLogado;
    private ListView<Evento> eventosListView;
    private TextField searchField;
    private ComboBox<String> categoriaComboBox;
    private TextArea detalhesArea;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @Override
    public void start(Stage primaryStage) {
        fachada.CarregarArquivos();
        primaryStage.setTitle("Participar de Eventos");

        List<Evento> eventos = fachada.ListarEventos();
        System.out.println("Eventos cadastrados:");
        for (Evento ev : eventos) {
            System.out.println(ev.getID() + "-" + ev.getTitulo() + " - " + ev.getDescricao() + " - " + ev.getID() + " - " + usuarioLogado.getNomeUsuario());
        }

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Painel superior para busca
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(10));

        searchField = new TextField();
        searchField.setPromptText("Buscar eventos...");
        searchField.setPrefWidth(300);

        Button refreshButton = new Button("Atualizar");
        refreshButton.setOnAction(e -> carregarEventos());

        topPanel.getChildren().addAll(
                new Label("Buscar:"),
                searchField,
                refreshButton
        );
        root.setTop(topPanel);

        // Menu lateral com categorias
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(10));
        leftMenu.setPrefWidth(200);

        Label filtroLabel = new Label("Filtrar por:");
        filtroLabel.setStyle("-fx-font-weight: bold;");

        categoriaComboBox = new ComboBox<>();
        categoriaComboBox.getItems().addAll("Todos", "Música", "Teatro", "Esportes", "Arte");
        categoriaComboBox.getSelectionModel().selectFirst();

        leftMenu.getChildren().addAll(filtroLabel, categoriaComboBox);
        root.setLeft(leftMenu);

        // Lista de eventos
        eventosListView = new ListView<>();
        eventosListView.setPlaceholder(new Label("Carregando eventos..."));
        eventosListView.setCellFactory(param -> new ListCell<Evento>() {
            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox box = new VBox(5);
                    Label titulo = new Label(evento.getTitulo());
                    titulo.setStyle("-fx-font-weight: bold;");

                    Label detalhes = new Label(
                            String.format("%s | %s | %s",
                                    evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    evento.getEndereco().getCidade(),
                                    evento.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm"))
                            ));

                    box.getChildren().addAll(titulo, detalhes);
                    setGraphic(box);
                }
            }
        });

        // Painel de detalhes à direita
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setPrefWidth(300);

        Label detalhesLabel = new Label("Detalhes do Evento");
        detalhesLabel.setStyle("-fx-font-weight: bold;");

        detalhesArea = new TextArea();
        detalhesArea.setEditable(false);
        detalhesArea.setWrapText(true);

        Button participarButton = new Button("Participar do Evento");
        participarButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");
        participarButton.setMaxWidth(Double.MAX_VALUE);

        rightPanel.getChildren().addAll(detalhesLabel, detalhesArea, participarButton);
        root.setRight(rightPanel);

        // Configura eventos
        configurarEventos(primaryStage, participarButton);

        // Carrega os eventos imediatamente
        carregarEventos();

        // Adiciona a lista ao centro com scroll
        ScrollPane scrollPane = new ScrollPane(eventosListView);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarEventos(Stage stage, Button participarButton) {
        // Filtro de busca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrarEventos();
        });

        // Filtro de categoria
        categoriaComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filtrarEventos();
        });

        // Seleção de evento
        eventosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetalhesEvento(newVal);
            }
        });

        // Botão participar
        participarButton.setOnAction(e -> {
            Evento selecionado = eventosListView.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                participarEvento(selecionado);
            }
        });
    }

    private void carregarEventos() {
        try {
            List<Evento> eventos = fachada.ListarEventos();
            if (eventos.isEmpty()) {
                eventosListView.setPlaceholder(new Label("Nenhum evento cadastrado."));
            } else {
                eventosListView.getItems().setAll(FXCollections.observableArrayList(eventos));
            }
        } catch (Exception e) {
            eventosListView.setPlaceholder(new Label("Erro ao carregar eventos."));
            System.err.println("Erro ao carregar eventos: " + e.getMessage());
        }
    }

    private void filtrarEventos() {
        String termoBusca = searchField.getText().toLowerCase();
        String categoriaSelecionada = categoriaComboBox.getValue();

        List<Evento> eventosFiltrados = fachada.ListarEventos().stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(termoBusca) ||
                        e.getDescricao().toLowerCase().contains(termoBusca))
                .filter(e -> categoriaSelecionada.equals("Todos") ||
                        e.getCategoria().equalsIgnoreCase(categoriaSelecionada))
                .collect(Collectors.toList());

        eventosListView.getItems().setAll(FXCollections.observableArrayList(eventosFiltrados));
    }

    private void mostrarDetalhesEvento(Evento evento) {
        String detalhes = String.format(
                "Título: %s\n\nDescrição: %s\n\nData: %s\nHorário: %s às %s\n\n" +
                        "Local: %s, %s\n\nIngressos disponíveis: %d\nValor: R$ %.2f\n\n" +
                        "Organizador: %s",
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                evento.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                evento.getHoraFim().format(DateTimeFormatter.ofPattern("HH:mm")),
                evento.getEndereco().getBairro(),
                evento.getEndereco().getCidade(),
                evento.getQtdeIngressos(),
                evento.getValorBase(),
                evento.getAnfitriao().getNomeCompleto()
        );

        detalhesArea.setText(detalhes);
    }

    private void participarEvento(Evento evento) {
        try {
            //fachada.participarEvento(usuarioLogado, evento);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                    "Você está participando do evento: " + evento.getTitulo());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                    "Não foi possível participar do evento: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}