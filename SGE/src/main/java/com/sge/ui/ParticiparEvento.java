/*import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ParticiparEvento extends Application {

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

    public class ParticiparEventoApp extends Application {

        // Lista de eventos
        private ObservableList<Evento> eventos;

        @Override
        public void start(Stage primaryStage) {
            // Inicializando a lista de eventos
            eventos = FXCollections.observableArrayList();
             // Carregar eventos de exemplo

            // Criando a tabela
            TableView<Evento> tabelaEventos = new TableView<>();
            tabelaEventos.setItems(eventos);

            // Definindo as colunas da tabela
            TableColumn<Evento, String> colunaTitulo = new TableColumn<>("Título");
            colunaTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));

            TableColumn<Evento, String> colunaDescricao = new TableColumn<>("Descrição");
            colunaDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));

            TableColumn<Evento, String> colunaCategoria = new TableColumn<>("Categoria");
            colunaCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));

            TableColumn<Evento, String> colunaEndereco = new TableColumn<>("Endereço");
            colunaEndereco.setCellValueFactory(cellData -> {
                Endereco endereco = cellData.getValue().getEndereco();
                return new SimpleStringProperty(endereco.getRua() + ", " + endereco.getNumero() + ", " +
                        endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getEstado() + ", " +
                        endereco.getCep());
            });

            TableColumn<Evento, String> colunaData = new TableColumn<>("Data do Evento");
            colunaData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataDoEvento()));

            TableColumn<Evento, String> colunaHoraInicio = new TableColumn<>("Hora Início");
            colunaHoraInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraInicio()));

            TableColumn<Evento, String> colunaHoraFim = new TableColumn<>("Hora Fim");
            colunaHoraFim.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraFim()));

            TableColumn<Evento, Integer> colunaIngressos = new TableColumn<>("Qtde Ingressos");
            colunaIngressos.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQtdeIngressos()).asObject());

            TableColumn<Evento, Double> colunaValor = new TableColumn<>("Valor Base");
            colunaValor.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValorBase()).asObject());

            TableColumn<Evento, String> colunaAnfitriao = new TableColumn<>("Anfitrião");
            colunaAnfitriao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuarioAnfitriao()));

            // Adicionando colunas à tabela
            tabelaEventos.getColumns().addAll(colunaTitulo, colunaDescricao, colunaCategoria, colunaEndereco,
                    colunaData, colunaHoraInicio, colunaHoraFim, colunaIngressos, colunaValor, colunaAnfitriao);

            // Botão para participar do evento
            Button btnParticipar = new Button("Participar do Evento");
            btnParticipar.setOnAction(e -> {
                Evento eventoSelecionado = tabelaEventos.getSelectionModel().getSelectedItem();
                if (eventoSelecionado != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Participar de Evento");
                    alert.setHeaderText(null);
                    alert.setContentText("Você participou do evento: " + eventoSelecionado.getTitulo());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Atenção");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor, selecione um evento para participar.");
                    alert.showAndWait();
                }
            });

            // Layout principal
            VBox vbox = new VBox(tabelaEventos, btnParticipar);
            BorderPane root = new BorderPane();
            root.setCenter(vbox);

            // Configuração da cena
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Participar de Evento");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    public static void main(String[] args) {
        launch(args);
    }
}
*/