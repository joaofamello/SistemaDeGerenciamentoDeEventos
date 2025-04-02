package com.sge.negocio.entidade;
import com.sge.dados.*;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GerenciadorEntrada {
    private final Scanner sc = new Scanner(System.in);
    private Usuario usuario;
    private NegocioUsuario negocioUsuario;
    private NegocioEvento negocioEvento;

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
        LocalDate data = recebeData();
        System.out.println("De que horas começará seu evento?");
        LocalDateTime horaInicio = recebeHora();
        System.out.println("De que horas terminará?");
        LocalDateTime horaFim = recebeHora();
        System.out.println("Quantas vagas terá o seu evento?");
        int qtdeIngressos = sc.nextInt();
        return new Evento(titulo, descricao, categoria, endereco, data, horaInicio, horaFim, qtdeIngressos, usuario);
    }

    public Endereco criarEndereco(){
        System.out.println("Qual é o nome da rua?");
        String rua = sc.nextLine();
        System.out.println("Qual é o bairro?");
        String bairro = sc.nextLine();
        System.out.println("Qual é o número?");
        int numero = sc.nextInt();
        System.out.println("Qual a cidade?");
        String cidade = sc.nextLine();
        System.out.println("Qual é o estado?");
        String estado = sc.nextLine();
        System.out.println("Digite o cep: ");
        String cep = sc.nextLine();
        return new Endereco (rua, bairro, numero, cep, cidade, estado);
    }

    public void cadastrarUsuario(){
        String nomeCompleto = sc.nextLine();
        String nomeUsuario = sc.nextLine();
        String email = sc.nextLine();
        String telefone = sc.nextLine();
        String senha = sc.nextLine();
        Usuario usuario = new Usuario(nomeCompleto,nomeUsuario,email,telefone,senha);
        negocioUsuario.inserir(usuario);
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
