package dojosys.app;

import dojosys.modelo.Aluno;
import dojosys.modelo.Instrutor;
import dojosys.servico.DojoManager;

import java.util.Scanner;

/**
 * Menu de interação do DojôSys (console).
 *
 * Responsável: João Gabriel.
 * O menu só conversa com o DojoManager (Singleton) e com as classes de domínio;
 * ele não guarda dados próprios. Assim, quando a lógica de negócio (Davi) e o
 * banco (Wladmir) evoluírem, o menu continua funcionando sem mudanças grandes.
 */
public class Menu {

    private static final String[] FAIXAS = {"Branca", "Azul", "Roxa", "Marrom", "Preta"};

    private final Scanner scanner;
    private final DojoManager dojo;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.dojo = DojoManager.getInstancia();
    }

    public void iniciar() {
        int opcao;
        do {
            exibirOpcoes();
            opcao = lerInteiro("Escolha uma opção: ");
            System.out.println();

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> cadastrarInstrutor();
                case 3 -> listarMembros();
                case 4 -> emDesenvolvimento("Promover faixa");
                case 5 -> emDesenvolvimento("Adicionar grau");
                case 6 -> emDesenvolvimento("Controle de mensalidades");
                case 0 -> System.out.println("Encerrando o DojôSys. Oss!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);
    }

    private void exibirOpcoes() {
        System.out.println("========================================");
        System.out.println("              DOJÔSYS                   ");
        System.out.println("   Sistema de Gestão de Jiu-Jitsu       ");
        System.out.println("========================================");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Cadastrar instrutor");
        System.out.println("3 - Listar membros");
        System.out.println("4 - Promover faixa");
        System.out.println("5 - Adicionar grau");
        System.out.println("6 - Controle de mensalidades");
        System.out.println("0 - Sair");
        System.out.println("----------------------------------------");
    }

    private void cadastrarAluno() {
        System.out.println("--- Cadastro de Aluno ---");
        String nome = lerTextoObrigatorio("Nome: ");
        String faixa = escolherFaixa();
        boolean mensalidadeEmDia = lerSimNao("Mensalidade em dia? (s/n): ");

        dojo.cadastrarMembro(new Aluno(nome, faixa, mensalidadeEmDia));
        System.out.println("Aluno " + nome + " cadastrado com sucesso!");
    }

    private void cadastrarInstrutor() {
        System.out.println("--- Cadastro de Instrutor ---");
        String nome = lerTextoObrigatorio("Nome: ");
        String faixa = escolherFaixa();
        double salario = lerDecimal("Salário (R$): ");

        dojo.cadastrarMembro(new Instrutor(nome, faixa, salario));
        System.out.println("Instrutor " + nome + " cadastrado com sucesso!");
    }

    private void listarMembros() {
        System.out.println("--- Membros do Dojô ---");
        dojo.listarTodos();
    }

    /**
     * Opções que dependem de métodos ainda não criados no DojoManager
     * (por exemplo, buscar um praticante pelo nome). Serão ligadas assim que
     * a parte de lógica de negócio estiver na main.
     */
    private void emDesenvolvimento(String funcionalidade) {
        System.out.println(funcionalidade + ": em desenvolvimento.");
    }

    // ---------- Leitura e validação de entradas ----------

    private String escolherFaixa() {
        System.out.println("Faixas disponíveis:");
        for (int i = 0; i < FAIXAS.length; i++) {
            System.out.println("  " + (i + 1) + " - " + FAIXAS[i]);
        }
        while (true) {
            int escolha = lerInteiro("Número da faixa: ");
            if (escolha >= 1 && escolha <= FAIXAS.length) {
                return FAIXAS[escolha - 1];
            }
            System.out.println("Faixa inválida. Escolha entre 1 e " + FAIXAS.length + ".");
        }
    }

    private String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este campo é obrigatório.");
        }
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(entrada);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("O valor não pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido (ex.: 2500.00).");
            }
        }
    }

    private boolean lerSimNao(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.equals("s") || entrada.equals("sim")) {
                return true;
            }
            if (entrada.equals("n") || entrada.equals("nao") || entrada.equals("não")) {
                return false;
            }
            System.out.println("Responda com 's' ou 'n'.");
        }
    }
}
