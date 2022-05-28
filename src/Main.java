import realbank.Banco;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Banco realBank = new Banco("Real Bank");

        int opcao = 0;
        boolean sair = false;
        do {

            System.out.println("===============================");
            System.out.println("REAL BANK");
            System.out.println("===============================");

            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Abrir conta");
            System.out.println("3 - Fazer deposito");
            System.out.println("4 - Fazer saque");
            System.out.println("5 - Fazer transferencia");
            System.out.println("6 - Extrato");
            System.out.println("7 - Listar clientes");
            System.out.println("8 - Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    realBank.cadastrarCliente();
                    break;
                case 2:
                    realBank.abrirConta();
                    break;
                case 3:
                    realBank.fazerDeposito();
                    break;
                case 4:
                    realBank.fazerSaque();
                    break;
                case 5:
                    realBank.fazerTransferencia();
                    break;
                case 6:
                    realBank.fazerExtrato();
                    break;
                case 7:
                    realBank.listar();
                    break;
                case 8:
                    sair = true;
                    break;
                default:
                    System.out.println("Opcao Invalida!");
                    break;
            }

            if (sair) {
                System.out.println("Saindo do sistema...");
            }

        } while (!sair);

    }
}