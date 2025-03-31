package com.sge.negocio.entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GerenciadorEntrada {
    private final Scanner sc = new Scanner(System.in);
    private Usuario usuario;

    public GerenciadorEntrada() {
    }

    public Evento criarEvento(Usuario usuario){
        System.out.println("Qual será o título do seu evento?");
        String titulo = sc.nextLine();
        System.out.println("Digite a descrição do seu evento:");
        String descricao = sc.nextLine();
        System.out.println("Qual é a categoria do evento?");
        String categoria = sc.nextLine();
        System.out.println("Onde será o seu evento?");
        Endereco endereco = criarEndereco();
        System.out.println("Quando será o seu evento?");
        LocalDate data = LocalDate.parse(sc.nextLine());
        System.out.println("De que horas começará seu evento?");
        LocalDateTime horaInicio = LocalDateTime.parse(sc.nextLine());
        System.out.println("De que horas terminará?");
        LocalDateTime horaFim = LocalDateTime.parse(sc.nextLine());
        System.out.println("Quantas vagas terá o seu evento?");
        int qtdeIngressos = sc.nextInt();
        return new Evento(titulo, descricao, categoria, endereco, data, horaInicio, horaFim, qtdeIngressos, usuario);
    }

    public Endereco criarEndereco(){
        String rua = sc.nextLine();
        String bairro = sc.nextLine();
        int numero = sc.nextInt();
        String cidade = sc.nextLine();
        String estado = sc.nextLine();
        String cep = sc.nextLine();
        String pais = sc.nextLine();
        return new Endereco (rua, bairro, numero, cep, cidade, estado, pais);
    }

    public Usuario cadastrarUsuario(){
        String nomeCompleto = sc.nextLine();
        String nomeUsuario = sc.nextLine();
        String email = sc.nextLine();
        String telefone = sc.nextLine();
        String senha = sc.nextLine();
        return new Usuario(nomeCompleto, nomeUsuario, email, telefone, senha);
    }

    public String recebeString(){
        return sc.nextLine();
    }

    public double recebeDouble(){
        return sc.nextDouble();
    }

    public int recebeInt(){
        return sc.nextInt();
    }

    public LocalDate recebeData(){
        return LocalDate.parse(sc.nextLine());
    }

    public LocalDateTime recebeHora(){
        return LocalDateTime.parse(sc.nextLine());
    }
}
