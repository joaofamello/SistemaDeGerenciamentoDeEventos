package com.sge.ui;

import com.sge.fachada.SGE;
import com.sge.negocio.entidade.Evento;
import com.sge.negocio.entidade.Usuario;
import com.sge.negocio.entidade.ingresso.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class ComprarIngresso extends Application {
    private SGE fachada = SGE.getInstancia();
    private Usuario usuarioLogado;
    private Evento eventoSelecionado;

    private ComboBox<String> tipoIngressoCombo;
    private Label valorLabel;
    private Label eventoLabel;
    private Label disponivelLabel;
    private TextArea detalhesArea;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void setEventoSelecionado(Evento evento) {
        this.eventoSelecionado = evento;
        // Só atualiza as informações se os componentes já foram inicializados
        if (eventoLabel != null) {
            atualizarInformacoesEvento();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Comprar Ingresso - SGE");

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Painel superior - informações do evento
        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(10));

        eventoLabel = new Label();
        eventoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        disponivelLabel = new Label();
        disponivelLabel.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold;");

        topPanel.getChildren().addAll(eventoLabel, disponivelLabel);
        root.setTop(topPanel);

        // Painel central - seleção de ingresso
        GridPane centerPanel = new GridPane();
        centerPanel.setHgap(10);
        centerPanel.setVgap(10);
        centerPanel.setPadding(new Insets(20));

        Label tipoLabel = new Label("Tipo de Ingresso:");
        tipoIngressoCombo = new ComboBox<>();
        tipoIngressoCombo.getItems().addAll("Inteiro", "Meia", "Social");
        tipoIngressoCombo.getSelectionModel().selectFirst();

        Label valorTextLabel = new Label("Valor:");
        valorLabel = new Label();
        valorLabel.setStyle("-fx-font-weight: bold;");

        tipoIngressoCombo.setOnAction(e -> atualizarValorIngresso());

        centerPanel.add(tipoLabel, 0, 0);
        centerPanel.add(tipoIngressoCombo, 1, 0);
        centerPanel.add(valorTextLabel, 0, 1);
        centerPanel.add(valorLabel, 1, 1);

        root.setCenter(centerPanel);

        // Painel inferior - detalhes e botões
        VBox bottomPanel = new VBox(15);
        bottomPanel.setPadding(new Insets(20));

        detalhesArea = new TextArea();
        detalhesArea.setEditable(false);
        detalhesArea.setPrefHeight(100);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button comprarButton = new Button("Comprar Ingresso");
        comprarButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");
        comprarButton.setOnAction(e -> comprarIngresso());

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setStyle("-fx-base: #F44336; -fx-text-fill: white;");
        cancelarButton.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(comprarButton, cancelarButton);
        bottomPanel.getChildren().addAll(new Label("Detalhes:"), detalhesArea, buttonBox);

        root.setBottom(bottomPanel);

        // Configuração da cena
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);

        if (eventoSelecionado != null) {
            atualizarInformacoesEvento();
        }

        // Atualiza os valores iniciais
        atualizarValorIngresso();
        primaryStage.show();

    }

    private void atualizarInformacoesEvento() {
        if (eventoSelecionado != null) {
            eventoLabel.setText("Evento: " + eventoSelecionado.getTitulo());
            disponivelLabel.setText("Ingressos disponíveis: " + eventoSelecionado.getIngressosDisponiveis());

            String detalhes = String.format(
                    "Data: %s\nHorário: %s às %s\nLocal: %s\nDescrição: %s",
                    eventoSelecionado.getData(),
                    eventoSelecionado.getHoraInicio().toLocalTime(),
                    eventoSelecionado.getHoraFim().toLocalTime(),
                    eventoSelecionado.getEndereco().enderecoFormatado(),
                    eventoSelecionado.getDescricao()
            );
            detalhesArea.setText(detalhes);
        }
    }

    private void atualizarValorIngresso() {
        if (eventoSelecionado == null) return;

        String tipoSelecionado = tipoIngressoCombo.getValue();
        double valorBase = eventoSelecionado.getValorBase();
        double valorFinal = valorBase;
        Ingresso ingresso;

        switch (tipoSelecionado) {
            case "Inteiro":
                ingresso = new IngressoInteiro(valorBase);
                valorFinal = ingresso.calcularValorFinal();
                break;
            case "Meia":
                ingresso = new IngressoMeia(valorBase);
                valorFinal = ingresso.calcularValorFinal();
                break;
            case "Social":
                ingresso = new IngressoSocial(valorBase);
                valorFinal = ingresso.calcularValorFinal();
                break;
        }

        valorLabel.setText(String.format("R$ %.2f", valorFinal));
    }

    private void comprarIngresso() {
        if (eventoSelecionado == null || usuarioLogado == null) {
            mostrarAlerta("Erro", "Nenhum evento selecionado ou usuário não logado.");
            return;
        }

        if (eventoSelecionado.getIngressosDisponiveis() <= 0) {
            mostrarAlerta("Esgotado", "Não há ingressos disponíveis para este evento.");
            return;
        }

        String tipoSelecionado = tipoIngressoCombo.getValue();
        Ingresso ingresso;

        try {
            switch (tipoSelecionado) {
                case "Inteiro":
                    ingresso = new IngressoInteiro(eventoSelecionado, eventoSelecionado.getValorBase());
                    break;
                case "Meia":
                    ingresso = new IngressoMeia(eventoSelecionado, eventoSelecionado.getValorBase());
                    break;
                case "Social":
                    ingresso = new IngressoSocial(eventoSelecionado, eventoSelecionado.getValorBase());
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de ingresso inválido");
            }

            // Realiza a venda do ingresso
            fachada.comprarIngresso(usuarioLogado, eventoSelecionado, ingresso);

            List<Usuario> usuarios = eventoSelecionado.getParticipantes();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getID() + "-" + usuario.getNomeUsuario());
            }

            fachada.SalvarArquivoUsuario();
            fachada.SalvarArquivoEvento();

            System.out.println("\nQuantidade de ingressos disponiveis: " + eventoSelecionado.getIngressosDisponiveis());

            // Exibe confirmação
            mostrarAlerta("Sucesso", "Ingresso comprado com sucesso!\n" +
                    "Tipo: " + tipoSelecionado + "\n" +
                    "Valor: R$" + ingresso.calcularValorFinal() + "\n" +
                    "Código: " + ingresso.getID());

            // Fecha a janela
            ((Stage) valorLabel.getScene().getWindow()).close();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao comprar ingresso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}