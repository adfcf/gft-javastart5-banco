package realbank.contas;

import realbank.Cliente;
import realbank.contas.Conta;

public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente possuidor, int id, int agencia) {
        super(possuidor, id, agencia);
    }

    @Override
    public boolean transferir(Conta outraConta, double valor) {
        return super.transferir(outraConta, valor);
    }

    @Override
    public boolean sacar(double valor) {
        return super.sacar(valor);
    }
}
