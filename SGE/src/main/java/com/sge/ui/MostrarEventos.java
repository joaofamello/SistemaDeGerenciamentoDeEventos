package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.GerenciadorEventos;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.CategoriaNaoEncontradaException;
import com.sge.negocio.excecao.CidadeSemEventosException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MostrarEventos extends Application {
    private SGE fachada = SGE.getInstancia();
    private Usuario usuarioLogado;
    private ListView<Evento> eventosListView;
    private TextField searchField;
    private TextArea detalhesArea;
    private ComboBox<ComboBoxItem> tipoFiltroComboBox;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    // Classe auxiliar para os itens do ComboBox
    public class ComboBoxItem {
        private String value;
        private String displayText;

        public ComboBoxItem(String value, String displayText) {
            this.value = value;
            this.displayText = displayText;
        }

        public String getValue() {
            return value;
        }

        public String getDisplayText() {
            return displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Meus Eventos");

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Painel superior para busca
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(10));

        searchField = new TextField();
        searchField.setPromptText("Digite o termo de busca...");
        searchField.setPrefWidth(300);

        Button refreshButton = new Button("Atualizar");
        refreshButton.setOnAction(e -> carregarEventos());

        topPanel.getChildren().addAll(
                new Label("Buscar:"),
                searchField,
                refreshButton
        );
        root.setTop(topPanel);

        // Menu lateral com tipos de filtro
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(10));
        leftMenu.setPrefWidth(200);

        Label filtroLabel = new Label("Filtrar por:");
        filtroLabel.setStyle("-fx-font-weight: bold;");

        tipoFiltroComboBox = new ComboBox<>();
        tipoFiltroComboBox.getItems().addAll(
                new ComboBoxItem("Todos", "Todos os campos"),
                new ComboBoxItem("Titulo", "Título do evento"),
                new ComboBoxItem("Categoria", "Categoria do evento"),
                new ComboBoxItem("Cidade", "Cidade do evento")
        );

        tipoFiltroComboBox.setConverter(new StringConverter<ComboBoxItem>() {
            @Override
            public String toString(ComboBoxItem item) {
                return item.getDisplayText();
            }

            @Override
            public ComboBoxItem fromString(String string) {
                return tipoFiltroComboBox.getItems().stream()
                        .filter(item -> item.getDisplayText().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        tipoFiltroComboBox.getSelectionModel().selectFirst();

        leftMenu.getChildren().addAll(filtroLabel, tipoFiltroComboBox);
        root.setLeft(leftMenu);

        // Lista de eventos
        eventosListView = new ListView<>();
        eventosListView.setPlaceholder(new Label("Carregando meus eventos..."));
        eventosListView.setPrefHeight(600);
        eventosListView.setPrefWidth(500);
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
                    String Estado = GerenciadorEventos.RetornarEstado(evento);
                    if(Estado.equalsIgnoreCase("Inativo")){
                        titulo = new Label(evento.getTitulo() + " (Cancelado)");
                        titulo.setStyle("-fx-text-fill: #C74C3FFF; -fx-font-weight: bold;");
                    } else if(Estado.equalsIgnoreCase("Lotado")){
                        titulo = new Label(evento.getTitulo() + " (Lotado)");
                        titulo.setStyle("-fx-text-fill: #355ab8; -fx-font-weight: bold;");
                    }else if(Estado.equalsIgnoreCase("Encerrado")){
                        titulo = new Label(evento.getTitulo() + " (Encerrado)");
                        titulo.setStyle("-fx-text-fill: #52ca3a; -fx-font-weight: bold;");
                    } else{
                        titulo = new Label(evento.getTitulo());
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

        Label detalhesLabel = new Label("Detalhes do Evento");
        detalhesLabel.setStyle("-fx-font-weight: bold;");

        detalhesArea = new TextArea();
        detalhesArea.setEditable(false);
        detalhesArea.setWrapText(true);
        detalhesArea.setPrefHeight(200);

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
        carregarEventos();
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
        try {
            String termoBusca = searchField.getText().trim();
            String tipoFiltro = tipoFiltroComboBox.getValue().getValue();

            List<Evento> eventosFiltrados;

            if (termoBusca.isEmpty()) {
                eventosFiltrados = fachada.ListarEventos();
            } else {
                switch (tipoFiltro) {
                    case "Titulo":
                        try {
                            eventosFiltrados = fachada.buscarEventoPorTitulo(termoBusca);
                        } catch (EventoNaoEncontradoException e) {
                            eventosFiltrados = new ArrayList<>();
                        }
                        break;

                    case "Categoria":
                        try {
                            eventosFiltrados = fachada.buscarEventoPorCategoria(termoBusca);
                        } catch (CategoriaNaoEncontradaException e) {
                            eventosFiltrados = new ArrayList<>();
                        }
                        break;

                    case "Cidade":
                        try {
                            eventosFiltrados = fachada.buscarEventoPorCidade(termoBusca);
                        } catch (CidadeSemEventosException e) {
                            eventosFiltrados = new ArrayList<>();
                        }
                        break;

                    default: // "Todos"
                        eventosFiltrados = fachada.ListarEventos().stream()
                                .filter(e -> e.getTitulo().toLowerCase().contains(termoBusca.toLowerCase()) ||
                                        e.getDescricao().toLowerCase().contains(termoBusca.toLowerCase()) ||
                                        e.getCategoria().toLowerCase().contains(termoBusca.toLowerCase()) ||
                                        e.getEndereco().getCidade().toLowerCase().contains(termoBusca.toLowerCase()))
                                .collect(Collectors.toList());
                }
            }

            eventosListView.getItems().setAll(FXCollections.observableArrayList(eventosFiltrados));
            eventosListView.setPlaceholder(eventosFiltrados.isEmpty() ?
                    new Label("Nenhum evento encontrado com os critérios selecionados.") : null);

        } catch (Exception e) {
            eventosListView.setPlaceholder(new Label("Erro ao filtrar eventos."));
            System.err.println("Erro ao filtrar eventos: " + e.getMessage());
        }
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
                evento.getIngressosDisponiveis(),
                evento.getValorBase(),
                evento.getAnfitriao().getNomeCompleto(),
               GerenciadorEventos.RetornarEstado(evento)
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
