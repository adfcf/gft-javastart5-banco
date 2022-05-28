package realbank;

import realbank.contas.Conta;
import realbank.contas.TipoConta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banco {

    public static final int MAXIMO_AGENCIAS = 5;

    private final Agencia[] agencias = new Agencia[MAXIMO_AGENCIAS];
    private final List<Cliente> clientes = new ArrayList<>();

    private int agenciasDisponiveis;

    private String nome;

    public Banco(String nome) {

        this.nome = nome;

        for (int i = 0; i < 3; ++i) {
            agencias[i] = new Agencia(this, (i + 1) * 1000, obterCEPNovaAgencia());
            ++agenciasDisponiveis;
        }

    }

    public void cadastrarCliente() {

        final int MAIORIDADE = 18;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente: ");
        final String nome = scanner.next();
        System.out.println("Digite o CPF do cliente: ");
        final String cpf = scanner.next();
        System.out.println("Digite a data de nascimento do cliente (dd/mm/aaaa): ");
        final String data = scanner.next().trim();
        final int dia = Integer.valueOf(data.substring(0, 2));
        final int mes = Integer.valueOf(data.substring(3, 5));
        final int ano = Integer.valueOf(data.substring(6));

        if (LocalDate.now().getYear() - ano <= MAIORIDADE) {
            System.out.println("ERRO! O cliente deve ter, no minimo, 18 anos.");
            confirmar(scanner);
            return;
        }

        final LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

        clientes.add(new Cliente(nome, cpf, dataNascimento));
        System.out.println("Cliente cadastrado com sucesso!");
        confirmar(scanner);

    }

    public void abrirConta() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o CPF do cliente: ");
        final String cpf = scanner.next();

        var cliente = clientes.stream().filter(c -> c.getCpf().equals(cpf)).findAny();
        if (cliente.isEmpty()) {
            System.out.println("Este cliente nao esta cadastrado no banco.");
            confirmar(scanner);
            return;
        }

        System.out.println("Digite o CEP preferivel da agencia: ");
        final String cep = scanner.next();

        Cliente c = cliente.get();
        String cepPreferivel = cep;

        Agencia agenciaPreferivel = null;
        for (Agencia a : agencias) {
            if (a == null) continue;
            if (a.getCEP().equals(cepPreferivel))
                agenciaPreferivel = a;
        }
        if (agenciaPreferivel == null) {
            System.out.println("Aviso: Nao ha nenhuma agencia associada a este CEP. Outra sera escolhida automaticamente.");
            agenciaPreferivel = agencias[(int)((agenciasDisponiveis - 1) * Math.random())];
        }

        System.out.println("Digite o tipo de conta (p/c): ");
        final String tipo = scanner.next();

        int id = agenciaPreferivel.abrirConta(c, (tipo.equalsIgnoreCase("p") ? TipoConta.POUPANCA : TipoConta.CORRENTE));
        System.out.println("CONTA CRIADA COM SUCESSO!");
        System.out.println("ID: " + id);
        System.out.println("Agencia: " + agenciaPreferivel.getNumero());
        confirmar(scanner);

    }

    public void fazerSaque() {

        System.out.println(" - MODO SAQUE - ");

        Scanner scanner = new Scanner(System.in);

        Conta contaRecuperada = recuperarConta();
        if (contaRecuperada == null) {
            System.out.println("NAO FOI ENCONTRADA CONTA ASSOCIADA A ESTE ID");
            confirmar(scanner);
            return;
        }

        System.out.println("Digite o valor a ser sacado: ");
        final double valor = scanner.nextDouble();

        contaRecuperada.sacar(valor);
        confirmar(scanner);

    }

    public void fazerDeposito() {

        System.out.println(" - MODO DEPOSITO - ");

        Scanner scanner = new Scanner(System.in);

        Conta contaRecuperada = recuperarConta();
        if (contaRecuperada == null) {
            System.out.println("NAO FOI ENCONTRADA CONTA ASSOCIADA A ESTE ID");
            confirmar(scanner);
            return;
        }

        System.out.println("Digite o valor a ser depositado: ");
        final double valor = scanner.nextDouble();

        contaRecuperada.depositar(valor);
        confirmar(scanner);

    }

    public void fazerTransferencia() {

        System.out.println(" - MODO TRANSFERENCIA - ");

        Scanner scanner = new Scanner(System.in);

        System.out.println("ORIGEM");
        Conta contaRecuperada = recuperarConta();
        if (contaRecuperada == null) {
            System.out.println("NAO FOI ENCONTRADA CONTA ASSOCIADA A ESTE ID");
            confirmar(scanner);
            return;
        }

        System.out.println("DESTINO");
        Conta contaParaTransferencia = recuperarConta();
        if (contaParaTransferencia == null) {
            System.out.println("NAO FOI ENCONTRADA CONTA ASSOCIADA A ESTE ID");
            confirmar(scanner);
            return;
        }

        System.out.println("Digite o valor a ser transferido: ");
        final double valor = scanner.nextDouble();

        contaRecuperada.transferir(contaParaTransferencia, valor);
        confirmar(scanner);

    }

    public void fazerExtrato() {

        System.out.println(" - MODO EXTRATO - ");

        Conta contaRecuperada = recuperarConta();
        if (contaRecuperada == null) {
            System.out.println("NAO FOI ENCONTRADA CONTA ASSOCIADA A ESTE ID");
            return;
        }

        System.out.println("SALDO: R$ " + contaRecuperada.getSaldo());
        contaRecuperada.imprimirHistorico();

        confirmar(new Scanner(System.in));

    }

    public void listar() {

        System.out.println("Listando clientes...");

        int contagem = 0;
        for (Cliente cliente : clientes) {
            System.out.println("" + (contagem + 1) + ". " + cliente.getNome() + "(CPF: " + cliente.getCpf() + ", Nasc.:" + cliente.getDataNascimento() + ")");
            for (Conta conta : cliente.getContas()) {
                System.out.println(" - Conta " + conta.getId() + " registrado na agencia " + conta.getAgencia());
            }
            System.out.println();
            ++contagem;
        }

        confirmar(new Scanner(System.in));

    }

    private void confirmar(Scanner scanner) {
        scanner.reset();
        System.out.println("Insira algo para confirmar...");
        scanner.next();
    }

    private String obterCEPNovaAgencia() {
        return String.valueOf((int) (Math.random() * 100)).concat(String.valueOf((int)(Math.random() * 1000000)));
    }

    private Conta recuperarConta() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da conta: ");
        final int id = scanner.nextInt();

        System.out.println("Digite o numero da agencia da conta: ");
        final int numeroAgencia = scanner.nextInt();

        Conta c = null;
        for (Agencia agencia : agencias) {

            if (agencia == null || agencia.getNumero() != numeroAgencia)
                continue;

            c = agencia.getConta(id);
            if (c != null)
                return c;

        }

        return null;

    }

}
