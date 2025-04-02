package com.sge.negocio.entidade;


import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;
import com.sge.dados.usuarios.RepositorioUsuariosArrayList;

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

    public void criarEvento(Usuario usuario){
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
        Evento evento = new Evento(titulo, descricao, categoria, endereco, data, horaInicio, horaFim, qtdeIngressos, usuario);
        negocioEvento.inserir(evento);
    }

    public Endereco criarEndereco(){
        System.out.println("Em qual estado será situado o seu evento?");
        String estado = sc.nextLine();
        System.out.println("Em qual cidade?");
        String cidade = sc.nextLine();
        System.out.println("Qual o cep do estabelecimento?");
        String cep = sc.nextLine();
        System.out.println("Qual o bairro?");
        String bairro = sc.nextLine();
        System.out.println("Qual a rua?");
        String rua = sc.nextLine();
        System.out.println("Qual o numero?");
        int numero = sc.nextInt();
        Endereco endereco = new Endereco(estado, cidade, cep, bairro, rua, numero);
        return endereco;
    }

    public void cadastrarUsuario(){
        System.out.println("Qual seu nome completo: ");
        String nomeCompleto = sc.nextLine();
        System.out.println("Qual seu nome de usuário: ");
        String nomeUsuario = sc.nextLine();
        System.out.println("Qual o seu email: ");
        String email = sc.nextLine();
        System.out.println("Qual o seu número de telefone:");
        String telefone = sc.nextLine();
        System.out.println("Digite uma senha para sua conta: ");
        String senha = sc.nextLine();
        //adicionar a condição de conta de 5 digitos ou mais
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
