import java.io.*;
import java.util.*;

public class Financeiro {
    private static final String ARQUIVO = "financas.txt";  // Arquivo fixo onde o resumo será salvo
    private List<Transacao> transacoes;

    public Financeiro() {
        transacoes = new ArrayList<>();
        carregarTransacoes();  // Carrega as transações quando o programa é iniciado
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        salvarResumoFinanceiro("");  // Salva o resumo financeiro após cada transação, sem filtro de período
    }

    // Método para salvar o resumo financeiro no arquivo 'financas.txt' para um período específico
    public void salvarResumoFinanceiro(String periodo) {
        double totalReceitas = calcularTotalReceitas(periodo);
        double totalDespesas = calcularTotalDespesas(periodo);
        double saldoTotal = calcularSaldoTotal(periodo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {  // Sobrescreve o arquivo com o resumo
            writer.write("Resumo Financeiro - Período: " + periodo + "\n");
            writer.write("Total de Receitas: R$ " + totalReceitas + "\n");
            writer.write("Total de Despesas: R$ " + totalDespesas + "\n");
            writer.write("Saldo Total: R$ " + saldoTotal + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega as transações do arquivo
    private void carregarTransacoes() {
        File arquivo = new File(ARQUIVO);  // Verifica se o arquivo existe
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();  // Cria o arquivo caso não exista
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Não carregamos mais as transações do arquivo, pois agora é apenas o resumo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para filtrar transações por período
    public List<Transacao> getTransacoesPorPeriodo(String periodo) {
        List<Transacao> filtro = new ArrayList<>();
        for (Transacao t : transacoes) {
            if (t.getData().contains(periodo)) {
                filtro.add(t);
            }
        }
        return filtro;
    }

    // Método para calcular o total de receitas em um determinado período
    public double calcularTotalReceitas(String periodo) {
        double total = 0;
        for (Transacao t : getTransacoesPorPeriodo(periodo)) {
            if (t.getTipo().equals("receita")) {
                total += t.getValor();
            }
        }
        return total;
    }

    // Método para calcular o total de despesas em um determinado período
    public double calcularTotalDespesas(String periodo) {
        double total = 0;
        for (Transacao t : getTransacoesPorPeriodo(periodo)) {
            if (t.getTipo().equals("despesa")) {
                total += t.getValor();
            }
        }
        return total;
    }

    // Método para calcular o saldo total (receitas - despesas) em um determinado período
    public double calcularSaldoTotal(String periodo) {
        return calcularTotalReceitas(periodo) - calcularTotalDespesas(periodo);
    }
}
