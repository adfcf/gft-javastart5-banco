package realbank.contas;

import jdk.swing.interop.SwingInterOpUtils;
import realbank.Cliente;

import java.util.Scanner;

public class ContaPoupanca extends Conta {

    public static final int MAX_RETIRADAS_MENSAIS = 3;

    public static final double TAXA_EXTENSAO = 10; // numerador percentual
    public static final double TAXA_FIXA_RETIRADA = 5; // reais

    private int retiradasEfetuadasMes;

    public ContaPoupanca(Cliente possuidor, int id, int agencia) {
        super(possuidor, id, agencia);
    }

    @Override
    public boolean sacar(double valor) {

        Scanner scanner = new Scanner(System.in);

        if (retiradasEfetuadasMes >= MAX_RETIRADAS_MENSAIS) {

            System.out.println("Aviso! Numero maximo de saques mensais efetuados!");
            System.out.println("Voce pode realizar o saque se pagar a taxa de extensao (" +
                    TAXA_EXTENSAO + "% do valor do saque).");

            System.out.println("Aceita? (s/n) ");
            String resposta = scanner.next().trim();
            if (resposta.equalsIgnoreCase("s")) {
                final double descontoExtensao = (TAXA_EXTENSAO / 100.0) * valor;
                System.out.println("Sera descontado R$ " + descontoExtensao + ", alem da taxa fixa de saque (R$ " + TAXA_FIXA_RETIRADA + ")");
                descontar(TAXA_FIXA_RETIRADA + descontoExtensao);
                return true;
            } else {
                System.out.println("Saque cancelado.");
            }
            return false;
        } else if (retiradasEfetuadasMes == 0) {
            System.out.println("SAQUE EM CONTA POUPANCA");
            System.out.println("- Primeira do mes! Nenhuma taxa sera cobrada.");
        } else {
            System.out.println("SAQUE EM CONTA POUPANCA");
            System.out.println("Taxa fixa de saque sera descontada (R$ " + TAXA_FIXA_RETIRADA + ")");
            descontar(TAXA_FIXA_RETIRADA);
        }

        retiradasEfetuadasMes++;
        return super.sacar(valor);

    }

    @Override
    public boolean transferir(Conta outraConta, double valor) {

        if (retiradasEfetuadasMes >= MAX_RETIRADAS_MENSAIS) {
            System.out.println("ERRO! Numero maximo de retiradas mensais efetuadas!");
            return false;
        } else if (retiradasEfetuadasMes == 0) {
            System.out.println("TRANSFERENCIA EM CONTA POUPANCA");
            System.out.println("- Primeira do mes! Nenhuma taxa sera cobrada.");
        } else {
            System.out.println("TRANSFERENCIA EM CONTA POUPANCA");
            System.out.println("Taxa fixa de retirada sera descontada (R$ " + TAXA_FIXA_RETIRADA + ")");
            descontar(TAXA_FIXA_RETIRADA);
        }

        retiradasEfetuadasMes++;
        return super.transferir(outraConta, valor);

    }

}
