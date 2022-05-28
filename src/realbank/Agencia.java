package realbank;

import realbank.contas.Conta;
import realbank.contas.ContaCorrente;
import realbank.contas.ContaPoupanca;
import realbank.contas.TipoConta;

import java.util.HashSet;
import java.util.Set;

public class Agencia {

    private final Banco bancoAssociado;
    private final int numero;

    private final Set<Conta> contas;

    private final String CEP;

    private int contadorContas = 0;

    public Agencia(Banco bancoAssociado, int numero, String cep) {
        this.CEP = cep;
        this.numero = numero;
        this.bancoAssociado = bancoAssociado;
        contas = new HashSet<>();
    }

    public int getNumero() {
        return numero;
    }

    public String getCEP() {
        return CEP;
    }

    public Conta getConta(int id) {
        for (Conta c : contas) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public int abrirConta(Cliente cliente, TipoConta tipo) {
        Conta novaConta;
        switch (tipo) {
            case CORRENTE:
                novaConta = new ContaCorrente(cliente, contadorContas, numero);
                contas.add(novaConta);
                cliente.addConta(novaConta);
                return contadorContas++;
            case POUPANCA:
                novaConta = new ContaPoupanca(cliente, contadorContas, numero);
                contas.add(novaConta);
                cliente.addConta(novaConta);
                return contadorContas++;
            default:
                System.out.println("INVALIDO!");
                return -1;
        }
    }

}
