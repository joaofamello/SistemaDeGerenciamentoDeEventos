package com.sge.negocio.entidade;


import com.sge.dados.eventos.IRepositorioEventos;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;
import com.sge.negocio.excecao.EmailJaExistenteException;
import com.sge.negocio.excecao.EventoDuplicadoException;
import com.sge.negocio.excecao.FormularioEventoInvalidoException;
import com.sge.negocio.excecao.FormularioUsuarioInvalidoException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GerenciadorEntrada {
    private final Scanner sc = new Scanner(System.in);
    private Usuario usuario;


    public GerenciadorEntrada() {
    }

    public void criarEvento(Usuario usuario, NegocioEvento evento) throws FormularioEventoInvalidoException, EventoDuplicadoException {
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

        System.out.println("Qual o valor base do ingresso?");
        double valorBase = sc.nextDouble();
        sc.nextLine(); // Limpar buffer

        System.out.println("Quantos ingressos estarão disponíveis?");
        int qtdeIngressos = sc.nextInt();
        sc.nextLine(); // Limpar buffer

        // Verifique se o usuário é um Anfitriao se necessário
        Evento eventoNovo = new Evento(
                titulo, descricao, categoria, endereco,
                data, horaInicio, horaFim,
                qtdeIngressos, valorBase, usuario
        );

        evento.inserir(eventoNovo);

    }

    public void AlterarEvento(Evento evento, NegocioEvento repositorio) throws FormularioEventoInvalidoException {
        int opcao;

        do {
            System.out.println("\n=== MENU DE ALTERAÇÃO DE EVENTO ===");
            System.out.println("1. Alterar título");
            System.out.println("2. Alterar descrição");
            System.out.println("3. Alterar data");
            System.out.println("4. Alterar endereço");
            System.out.println("5. Alterar categoria");
            System.out.println("6. Alterar quantidade de ingressos");
            System.out.println("0. Sair");
            System.out.print("Escolha o que deseja alterar: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch(opcao) {
                case 1:
                    System.out.print("Novo título: ");
                    String novoTitulo = sc.nextLine();
                    sc.nextLine();
                    repositorio.alterarTitulo(evento, novoTitulo);
                    System.out.println("Título alterado com sucesso!");
                    break;
                case 2:
                    System.out.print("Nova descrição: ");
                    String novaDescricao = sc.nextLine();
                    sc.nextLine();
                    repositorio.alterarDescricao(evento, novaDescricao);
                    System.out.println("Descrição alterada com sucesso!");
                    break;
                case 3:
                    System.out.print("Nova data (AAAA-MM-DD): ");
                    LocalDate novaData = LocalDate.parse(sc.nextLine());
                    sc.nextLine();
                    repositorio.alterarData(evento, novaData);
                    System.out.println("Data alterada com sucesso!");
                    break;
                case 4:
                    System.out.println("Novo endereço:");
                    System.out.print("Estado: ");
                    String estado = sc.nextLine();
                    System.out.print("Cidade: ");
                    String cidade = sc.nextLine();
                    System.out.print("CEP: ");
                    String cep = sc.nextLine();
                    System.out.print("Bairro: ");
                    String bairro = sc.nextLine();
                    System.out.print("Rua: ");
                    String rua = sc.nextLine();
                    System.out.print("Número: ");
                    int numero = sc.nextInt();
                    sc.nextLine();
                    Endereco novoEndereco = new Endereco(estado, cidade, cep, bairro, rua, numero);
                    repositorio.alterarEndereco(evento, novoEndereco);
                    System.out.println("Endereço alterado com sucesso!");
                    break;
                case 5:
                    System.out.print("Nova categoria: ");
                    String novaCategoria = sc.nextLine();
                    sc.nextLine();
                    repositorio.alterarCategoria(evento, novaCategoria);
                    System.out.println("Categoria alterada com sucesso!");
                    break;
                case 6:
                    System.out.print("Nova quantidade de ingressos: ");
                    int novaQtde = sc.nextInt();
                    sc.nextLine();
                    repositorio.alterarQtdeIngressos(evento, novaQtde);
                    System.out.println("Quantidade de ingressos alterada com sucesso!");
                    break;
                case 0:
                    System.out.println("Saindo do menu de alteração...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while(opcao != 0);
    }

    public Endereco criarEndereco(){
        System.out.println("Em qual estado será situado o seu evento?");
        String estado = sc.nextLine();
        sc.nextLine();
        System.out.println("Em qual cidade?");
        String cidade = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual o cep do estabelecimento?");
        String cep = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual o bairro?");
        String bairro = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual a rua?");
        String rua = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual o numero?");
        int numero = sc.nextInt();
        sc.nextLine();
        return new Endereco(estado, cidade, cep, bairro, rua, numero);
    }

    public void cadastrarUsuario(NegocioUsuario usuario) throws FormularioUsuarioInvalidoException, EmailJaExistenteException {
        System.out.println("Qual seu nome completo: ");
        String nomeCompleto = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual seu nome de usuário: ");
        String nomeUsuario = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual o seu email: ");
        String email = sc.nextLine();
        sc.nextLine();
        System.out.println("Qual o seu número de telefone:");
        String telefone = sc.nextLine();
        sc.nextLine();
        System.out.println("Digite uma senha para sua conta: ");
        String senha = sc.nextLine();
        sc.nextLine();
        Usuario usuarioNovo = new Usuario(nomeCompleto,nomeUsuario,email,telefone,senha);
        usuario.inserir(usuarioNovo);
    }

    public LocalDate recebeData(){
        return LocalDate.parse(sc.nextLine());
    }

    public LocalDateTime recebeHora(){
        return LocalDateTime.parse(sc.nextLine());
    }
}