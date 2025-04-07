package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MostrarEventos extends Application {
    private SGE fachada = SGE.getInstancia();
    private Usuario usuarioLogado;
    private ListView<Evento> eventosListView;
    private TextField searchField;
    private TextArea detalhesArea;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @Override
    public void start(Stage primaryStage) {
        fachada.CarregarArquivos();
        primaryStage.setTitle("Meus Eventos");

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Painel superior para busca
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(10));

        searchField = new TextField();
        searchField.setPromptText("Buscar meus eventos...");
        searchField.setPrefWidth(300);

        Button refreshButton = new Button("Atualizar");
        refreshButton.setOnAction(e -> carregarEventos());

        topPanel.getChildren().addAll(
                new Label("Buscar:"),
                searchField,
                refreshButton
        );
        root.setTop(topPanel);

        // Lista de eventos
        eventosListView = new ListView<>();
        eventosListView.setPlaceholder(new Label("Carregando meus eventos..."));
        eventosListView.setCellFactory(param -> new ListCell<Evento>() {
            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //Botar vermelho
                    VBox box = new VBox(5);
                    Label titulo;
                    if(!evento.getEstado()){
                        titulo = new Label(evento.getTitulo());
                        titulo.setStyle("-fx-text-fill: #C74C3FFF; -fx-font-weight: bold;");
                    }else {
                        titulo = new Label(evento.getTitulo());
                        titulo.setStyle("-fx-font-weight: bold;");
                    }

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

        // Painel de detalhes e botões à direita
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setPrefWidth(300);

        Label detalhesLabel = new Label("Detalhes do Evento");
        detalhesLabel.setStyle("-fx-font-weight: bold;");

        detalhesArea = new TextArea();
        detalhesArea.setEditable(false);
        detalhesArea.setWrapText(true);

        Button editarButton = new Button("Editar Evento");
        editarButton.setStyle("-fx-base: #2196F3; -fx-text-fill: white;");
        editarButton.setMaxWidth(Double.MAX_VALUE);

        Button cancelarButton = new Button("Cancelar Evento");
        cancelarButton.setStyle("-fx-base: #f44336; -fx-text-fill: white;");
        cancelarButton.setMaxWidth(Double.MAX_VALUE);

        rightPanel.getChildren().addAll(detalhesLabel, detalhesArea, editarButton, cancelarButton);
        root.setRight(rightPanel);

        // Configura eventos
        configurarEventos(primaryStage, editarButton, cancelarButton);

        // Carrega os eventos imediatamente
        carregarEventos();

        // Adiciona a lista ao centro com scroll
        ScrollPane scrollPane = new ScrollPane(eventosListView);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarEventos(Stage stage, Button editarButton, Button cancelarButton) {
        // Filtro de busca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrarEventos();
        });

        // Seleção de evento
        eventosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetalhesEvento(newVal);
                // Habilita/desabilita botões conforme seleção
                boolean meuEvento = newVal.getAnfitriao().equals(usuarioLogado);
                editarButton.setDisable(!meuEvento);
                cancelarButton.setDisable(!meuEvento);
            }
        });

        // Botão editar
        editarButton.setOnAction(e -> {
            Evento selecionado = eventosListView.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                editarEvento(selecionado);
            }
        });

        // Botão cancelar
        cancelarButton.setOnAction(e -> {
            Evento selecionado = eventosListView.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                cancelarEvento(selecionado);
            }
        });
    }

    private void carregarEventos() {
        try {
            List<Evento> eventos = fachada.ListarEventos().stream()
                    .filter(e -> e.getAnfitriao().equals(usuarioLogado))
                    .collect(Collectors.toList());

            if (eventos.isEmpty()) {
                eventosListView.setPlaceholder(new Label("Você não possui eventos cadastrados."));
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

        List<Evento> eventosFiltrados = fachada.ListarEventos().stream()
                .filter(e -> e.getAnfitriao().equals(usuarioLogado))
                .filter(e -> e.getTitulo().toLowerCase().contains(termoBusca) ||
                        e.getDescricao().toLowerCase().contains(termoBusca))
                .collect(Collectors.toList());

        eventosListView.getItems().setAll(FXCollections.observableArrayList(eventosFiltrados));
    }

    private void mostrarDetalhesEvento(Evento evento) {
        String detalhes = String.format(
                "Título: %s\n\nDescrição: %s\nCategoria: %s\n\nData: %s\nHorário: %s às %s\n\n" +
                        "Local: %s, %s\n\nIngressos disponíveis: %d\nValor: R$ %.2f\n\n" +
                        "Organizador: %s\n\nEstado: %s",
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getCategoria(),
                evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                evento.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                evento.getHoraFim().format(DateTimeFormatter.ofPattern("HH:mm")),
                evento.getEndereco().getBairro(),
                evento.getEndereco().getCidade(),
                evento.getQtdeIngressos(),
                evento.getValorBase(),
                evento.getAnfitriao().getNomeCompleto(),
                evento.getEstado() ? "Ativo" : "Cancelado"
        );

        detalhesArea.setText(detalhes);
    }

    private void editarEvento(Evento evento) {
        if(!evento.getEstado()){
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível editar o evento: " + evento.getTitulo() + " Evento cancelado.");
        }else {
            try {
                Stage editarStage = new Stage();
                EditarEvento editarApp = new EditarEvento();
                editarApp.setUsuarioLogado(usuarioLogado);
                editarApp.setEventoSelecionado(evento);
                editarApp.start(editarStage);

                // Fecha a janela atual
                //((Stage) eventosListView.getScene().getWindow()).close();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível editar o evento: " + e.getMessage());
            }
        }
    }

    private void cancelarEvento(Evento evento) {
        try {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Cancelamento");
            confirmacao.setHeaderText("Cancelar Evento");
            confirmacao.setContentText("Tem certeza que deseja cancelar o evento '" + evento.getTitulo() + "'?");

            if (confirmacao.showAndWait().get() == ButtonType.OK) {
                fachada.cancelarEvento(evento, usuarioLogado);
                fachada.SalvarArquivoEvento();
                carregarEventos(); // Atualiza a lista
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Evento cancelado com sucesso!");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível cancelar o evento: " + e.getMessage());
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
