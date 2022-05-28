package realbank;

import realbank.contas.Conta;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Cliente {

    private final String nome;
    private final String cpf;

    private final LocalDate dataNascimento;

    private final Set<Conta> contas;

    public Cliente(String nome, String cpf, LocalDate dataNascimento) {

        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;

        this.contas = new HashSet<>();

    }

    public void addConta(Conta conta) {
        contas.add(conta);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Set<Conta> getContas() {
        return contas;
    }

}
