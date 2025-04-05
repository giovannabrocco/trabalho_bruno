import java.io.*;
import java.util.*;

public class Financeiro {
    private static final String ARQUIVO = "financas.txt";
    private List<Transacao> transacoes;

    public Financeiro() {
        transacoes = new ArrayList<>();
        carregarTransacoes();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        salvarTransacoes();
    }

    private void salvarTransacoes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Transacao transacao : transacoes) {
                writer.write(transacao.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarTransacoes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String tipo = dados[0].trim();
                String descricao = dados[1].trim();
                double valor = Double.parseDouble(dados[2].trim());
                String data = dados[3].trim();
                Transacao transacao = new Transacao(tipo, descricao, valor, data);
                transacoes.add(transacao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Transacao> getTransacoesPorPeriodo(String periodo) {
        List<Transacao> filtro = new ArrayList<>();
        for (Transacao t : transacoes) {
            if (t.getData().contains(periodo)) {
                filtro.add(t);
            }
        }
        return filtro;
    }

    public double calcularTotalReceitas(String periodo) {
        double total = 0;
        for (Transacao t : getTransacoesPorPeriodo(periodo)) {
            if (t.getTipo().equals("receita")) {
                total += t.getValor();
            }
        }
        return total;
    }

    public double calcularTotalDespesas(String periodo) {
        double total = 0;
        for (Transacao t : getTransacoesPorPeriodo(periodo)) {
            if (t.getTipo().equals("despesa")) {
                total += t.getValor();
            }
        }
        return total;
    }

    public double calcularSaldoTotal(String periodo) {
        return calcularTotalReceitas(periodo) - calcularTotalDespesas(periodo);
    }
}
