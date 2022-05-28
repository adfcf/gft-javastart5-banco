package realbank.contas;

import realbank.Cliente;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private final int id;
    private final int agencia;

    private final Cliente possuidor;

    private final List<String> historico = new ArrayList<>();

    protected double saldo;

    public Conta(Cliente possuidor, int id, int agencia) {
        this.possuidor = possuidor;
        this.id = id;
        this.agencia = agencia;
    }

    public int getId() {
        return id;
    }

    public int getAgencia() {
        return agencia;
    }

    public Cliente getPossuidor() {
        return possuidor;
    }

    public double getSaldo() {
        return saldo;
    }

    public void imprimirHistorico() {
        for (int i = 0; i < historico.size(); ++i) {
            System.out.println("" + (i + 1) +  ". " + historico.get(i));
        }
    }

    public void depositar(double valor) {

        if (valor <= 0) {
            System.out.println("Valor Invalido. Nada sera feito");
            return;
        }

        saldo += valor;
        System.out.println("R$ " + valor + " depositado na conta de " + possuidor.getNome());

        historico.add("R$ " + valor + " depositado.");

    }

    protected boolean descontar(double valor) {
        if (valor <= 0)
            return false;
        saldo -= valor;
        return true;
    }

    public boolean sacar(double valor) {

        if (valor <= 0) {
            System.out.println("Valor Invalido!");
            return false;
        }

        if (saldo >= valor) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " efetuado com sucesso!");
            historico.add("Saque de R$ " + valor + " efetuado.");
            return true;
        }

        System.out.println("Saldo Insuficiente!");
        return false;

    }

    public boolean transferir(Conta outraConta, double valor) {

        if (valor <= 0) {
            System.out.println("Valor Invalido!");
            return false;
        }

        if (saldo >= valor) {
            saldo -= valor;
            outraConta.depositar(valor);
            System.out.println("Transferencia de R$ " + valor + " efetuada com sucesso!");

            historico.add("Transferiu R$ " + valor + " para " + outraConta.getPossuidor().getNome());
            outraConta.historico.add("Recebeu transferencia de R$ " + valor + " de " + getPossuidor().getNome());

            return true;
        }

        System.out.println("Saldo Insuficiente!");
        return false;

    }


}
