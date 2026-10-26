import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n========================================");
            System.out.println(" BLACK FRIDAY AMAZON");
            System.out.println(" Arvores Balanceadas");
            System.out.println("========================================");
            System.out.println("1 - Utilizar Arvore AVL");
            System.out.println("2 - Utilizar Arvore Rubro-Negra");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    executarAVL();
                    break;
                case 2:
                    executarRubroNegra();
                    break;
                case 0:
                    System.out.println("Programa encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void executarAVL() {
        ArvoreAVL arvore = new ArvoreAVL();
        int opcao;

        do {
            exibirMenuOperacoes("AVL");
            opcao = lerInteiro("Escolha uma operacao: ");

            switch (opcao) {
                case 1: {
                    int valor = lerInteiro("ID do pedido para inserir: ");

                    if (arvore.inserir(valor)) {
                        System.out.println("ID inserido com sucesso.");
                    } else {
                        System.out.println("ID duplicado. Insercao ignorada.");
                    }
                    break;
                }

                case 2: {
                    int valor = lerInteiro("ID do pedido para remover: ");

                    if (arvore.remover(valor)) {
                        System.out.println("ID removido com sucesso.");
                    } else {
                        System.out.println("ID nao encontrado.");
                    }
                    break;
                }

                case 3: {
                    int valor = lerInteiro("ID do pedido para buscar: ");

                    if (arvore.buscar(valor)) {
                        System.out.println("ID encontrado.");
                    } else {
                        System.out.println("ID nao encontrado.");
                    }
                    break;
                }

                case 4:
                    System.out.println("IDs em ordem crescente:");
                    arvore.imprimirEmOrdem();
                    break;

                case 5:
                    System.out.println("Estrutura da arvore AVL:");
                    arvore.imprimirEstrutura();
                    break;

                case 6:
                    exibirEstatisticas(
                            arvore.altura(),
                            arvore.getRotacoes(),
                            arvore.validar());
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void executarRubroNegra() {
        ArvoreRubroNegra arvore = new ArvoreRubroNegra();
        int opcao;

        do {
            exibirMenuOperacoes("Rubro-Negra");
            opcao = lerInteiro("Escolha uma operacao: ");

            switch (opcao) {
                case 1: {
                    int valor = lerInteiro("ID do pedido para inserir: ");

                    if (arvore.inserir(valor)) {
                        System.out.println("ID inserido com sucesso.");
                    } else {
                        System.out.println("ID duplicado. Insercao ignorada.");
                    }
                    break;
                }

                case 2: {
                    int valor = lerInteiro("ID do pedido para remover: ");

                    if (arvore.remover(valor)) {
                        System.out.println("ID removido com sucesso.");
                    } else {
                        System.out.println("ID nao encontrado.");
                    }
                    break;
                }

                case 3: {
                    int valor = lerInteiro("ID do pedido para buscar: ");

                    if (arvore.buscar(valor)) {
                        System.out.println("ID encontrado.");
                    } else {
                        System.out.println("ID nao encontrado.");
                    }
                    break;
                }

                case 4:
                    System.out.println("IDs em ordem crescente:");
                    arvore.imprimirEmOrdem();
                    break;

                case 5:
                    System.out.println("Estrutura da arvore Rubro-Negra:");
                    arvore.imprimirEstrutura();
                    break;

                case 6:
                    exibirEstatisticas(
                            arvore.altura(),
                            arvore.getRotacoes(),
                            arvore.validar());
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuOperacoes(String tipo) {
        System.out.println("\n----------------------------------------");
        System.out.println(" MENU DE OPERACOES - ARVORE " + tipo);
        System.out.println("----------------------------------------");
        System.out.println("1 - Inserir ID de pedido");
        System.out.println("2 - Remover ID de pedido");
        System.out.println("3 - Buscar ID de pedido");
        System.out.println("4 - Exibir IDs em ordem crescente");
        System.out.println("5 - Visualizar estrutura da arvore");
        System.out.println("6 - Exibir altura, rotacoes e validacao");
        System.out.println("0 - Voltar ao menu principal");
    }

    private static void exibirEstatisticas(
            int altura, long rotacoes, boolean valida) {

        System.out.println("\n--- Estatisticas da arvore ---");
        System.out.println("Altura: " + altura);
        System.out.println("Rotacoes realizadas: " + rotacoes);
        System.out.println("Balanceamento valido: "
                + (valida ? "SIM" : "NAO"));
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada invalida. Digite um numero inteiro.");
            }
        }
    }
}