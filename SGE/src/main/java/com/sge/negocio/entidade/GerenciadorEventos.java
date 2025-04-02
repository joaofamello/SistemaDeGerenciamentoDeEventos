package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GerenciadorEventos {
    private final Anfitriao operador;
    private GerenciadorEntrada entrada = new GerenciadorEntrada();

    public GerenciadorEventos(Anfitriao operador) {
        this.operador = operador;
    }

    //Métodos Básicos de Gerenciamento dos Eventos
    public void listarEventos() {
        if (operador.getEventosCriados() != null) {
            for (Evento evento : operador.getEventosCriados()) {
                System.out.println("\nTitulo: " + evento.getTitulo()
                        + "\nDescrição: " + evento.getDescricao()
                        + "\nCategoria: " + evento.getCategoria()
                        + "\nEndereço:  "
                        + "\nEstado: " + evento.getEndereco().getEstado()
                        + "\nCidade: " + evento.getEndereco().getCidade()
                        + "\nCEP: " + evento.getEndereco().getCep()
                        + "\nBairro: " + evento.getEndereco().getBairro()
                        + "\nRua: " + evento.getEndereco().getRua()
                        + "\nNumero: " + evento.getEndereco().getNumero()
                        + "\nTitulo: " + evento.getTitulo());
            }
        } else {
            System.out.println("Nenhum evento cadastrado!"); // Criar um exception para isso
        }
    }

    public Evento buscarEventoNome() {
        String nome = entrada.recebeString();
        for (Evento evento : operador.getEventosCriados()) {
            if(nome.equals(evento.getTitulo())) {
                return evento;
            }
        }
        return null; // usar o exception de nenhum evento cadastrado
    }
    public Evento buscarEventoID() {
        int ID = entrada.recebeInt();
        for (Evento evento : operador.getEventosCriados()) {
            if(ID == evento.getID()){
                return evento;
            }
        }
        return null;
    }

    public void mudarTituloEvento(Evento evento, String novoTitulo) {
        evento.setTitulo(novoTitulo);
    }

    public void mudarDescricaoEvento(Evento evento, String novaDescricao) {
        evento.setDescricao(novaDescricao);
    }

    public void mudarDataEvento(Evento evento, LocalDate novaData){
        evento.setData(novaData);
    }

    public void mudarHoraEvento(Evento evento, LocalDateTime novoInicio, LocalDateTime novoFim) {
        evento.setHoraInicio(novoInicio);
        evento.setHoraFim(novoFim);
    }

    public void mudarEnderecoEvento(Evento evento, Endereco endereco) {
        evento.setEndereco(endereco);
    }

    public void mudarCategoriaEvento(Evento evento, String categoria) {
        evento.setCategoria(categoria);
    }

    public void mudarQtdeIngressosEvento(Evento evento, int qtdeIngressos) {
        evento.setQtdeIngressos(qtdeIngressos);
    }

    public void excluirEvento(Evento evento) { // pensar melhor nisso aqui
        operador.getEventosCriados().remove(evento);
    }

    public void editarEvento(Evento evento) { // nisso aqui também
        // criar exceção para evento não encontrado
        // criar uma tela para edição

        switch(entrada.recebeInt()){
            case 1:
                evento.setTitulo(entrada.recebeString());
                break;
            case 2:
                evento.setDescricao(entrada.recebeString());
                break;
            case 3:
                evento.setCategoria(entrada.recebeString());
                break;
            case 4:
                Endereco novoEndereco = entrada.criarEndereco();
                evento.setEndereco(novoEndereco);
                break;
            case 5:
                evento.setQtdeIngressos(entrada.recebeInt());
                break;
            // ainda tem coisas para fazer
        }
    }
}
