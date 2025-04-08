package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.excecao.CategoriaNaoEncontradaException;
import com.sge.negocio.excecao.CidadeSemEventosException;
import com.sge.negocio.excecao.ErroCancelarInscricaoException;
import com.sge.negocio.excecao.EventoNaoEncontradoException;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class EventosParticipado extends Application {
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
        //fachada.CarregarArquivos();
        primaryStage.setTitle("Eventos que estou participando");

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
                    Label titulo;
                    if(!evento.getEstado()){
                        titulo = new Label(evento.getTitulo() + " (Cancelado)");
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


        Button cancelarParticipacaoButton = new Button("Cancelar Participação");
        cancelarParticipacaoButton.setStyle("-fx-base: #f44336; -fx-text-fill: white;");
        cancelarParticipacaoButton.setMaxWidth(Double.MAX_VALUE);

        rightPanel.getChildren().addAll(detalhesLabel, detalhesArea , cancelarParticipacaoButton);
        root.setRight(rightPanel);

        // Configura eventos
        configurarEventos(primaryStage, cancelarParticipacaoButton);

        // Carrega os eventos imediatamente
        carregarEventos();

        // Adiciona a lista ao centro com scroll
        ScrollPane scrollPane = new ScrollPane(eventosListView);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarEventos(Stage stage, Button cancelarParticipacaoButton) {
        // Filtro de busca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrarEventos();
        });

        // Seleção de evento
        eventosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetalhesEvento(newVal);
                // Verifica se o usuário está realmente participando deste evento
               // boolean participando = fachada.verificarParticipacaoEvento(usuarioLogado, newVal);
               // cancelarParticipacaoButton.setDisable(!participando);
            }
        });

        // Botão cancelar participação
        cancelarParticipacaoButton.setOnAction(e -> {
            Evento selecionado = eventosListView.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    cancelarParticipacao(selecionado);
                } catch (ErroCancelarInscricaoException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    private void carregarEventos() {
        try {
            List<Evento> eventos = fachada.listarEventosParticipantes(usuarioLogado);

            if (eventos.isEmpty()) {
                eventosListView.setPlaceholder(new Label("Você não está participando de nenhum evento."));
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
                evento.getQtdeIngressos(),
                evento.getValorBase(),
                evento.getAnfitriao().getNomeCompleto(),
                evento.getEstado() ? "Ativo" : "Cancelado"
        );

        detalhesArea.setText(detalhes);
    }

    private void cancelarParticipacao(Evento evento) throws ErroCancelarInscricaoException {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Cancelamento");
        confirmacao.setHeaderText("Cancelar Participação");
        confirmacao.setContentText("Tem certeza que deseja cancelar sua participação no evento '" + evento.getTitulo() + "'?");

        if (confirmacao.showAndWait().get() == ButtonType.OK){
            fachada.cancelarInscricao(usuarioLogado, evento);
            fachada.SalvarArquivoUsuario();
            fachada.SalvarArquivoEvento();
            evento.setQtdeIngressosVendidos(1);
            carregarEventos(); // Atualiza a lista
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Participação cancelada com sucesso!");
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