import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ResumoFinanceiro {
    private Financeiro financeiro;
    private JFrame frame;
    private JTextArea textoResumo;

    public ResumoFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
        frame = new JFrame("Resumo Financeiro");
        textoResumo = new JTextArea(10, 30);
        textoResumo.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textoResumo);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton btnMostrarResumo = new JButton("Mostrar Resumo");
        btnMostrarResumo.addActionListener(e -> exibirResumo());

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnMostrarResumo);
        frame.add(painelBotoes, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void exibirResumo() {
        String periodo = JOptionPane.showInputDialog(frame, "Digite o período (ex: 04/2025):");
        double totalReceitas = financeiro.calcularTotalReceitas(periodo);
        double totalDespesas = financeiro.calcularTotalDespesas(periodo);
        double saldoTotal = financeiro.calcularSaldoTotal(periodo);

        textoResumo.setText("Resumo financeiro para o período " + periodo + ":\n");
        textoResumo.append("Total de Receitas: R$ " + totalReceitas + "\n");
        textoResumo.append("Total de Despesas: R$ " + totalDespesas + "\n");
        textoResumo.append("Saldo Total: R$ " + saldoTotal + "\n");
    }
}
