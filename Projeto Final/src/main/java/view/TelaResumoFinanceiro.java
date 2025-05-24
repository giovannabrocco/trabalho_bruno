package view;

import controller.ResumoFinanceiroController;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class TelaResumoFinanceiro extends JFrame {
    private JFormattedTextField campoDataInicio;
    private JFormattedTextField campoDataFim;
    private JLabel labelReceitas;
    private JLabel labelDespesas;
    private JLabel labelSaldo;

    private ResumoFinanceiroController controller;

    public TelaResumoFinanceiro() {
        controller = new ResumoFinanceiroController();

        setTitle("Resumo Financeiro");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelFiltros.setBackground(Color.WHITE);
        campoDataInicio = new JFormattedTextField(createMaskFormatter("##/##/####"));
        campoDataFim = new JFormattedTextField(createMaskFormatter("##/##/####"));

        campoDataInicio.setColumns(8);
        campoDataFim.setColumns(8);

        painelFiltros.add(new JLabel("Data Início (dd/MM/yyyy):"));
        painelFiltros.add(campoDataInicio);
        painelFiltros.add(new JLabel("Data Fim (dd/MM/yyyy):"));
        painelFiltros.add(campoDataFim);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(Color.BLACK);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> atualizarResumo());
        painelFiltros.add(btnFiltrar);

        JPanel painelResultados = new JPanel(new GridLayout(3, 1, 10, 10));
        painelResultados.setBackground(Color.WHITE);
        painelResultados.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        labelReceitas = new JLabel("Total de Receitas: R$ 0,00", SwingConstants.CENTER);
        labelReceitas.setFont(new Font("Arial", Font.BOLD, 16));
        labelDespesas = new JLabel("Total de Despesas: R$ 0,00", SwingConstants.CENTER);
        labelDespesas.setFont(new Font("Arial", Font.BOLD, 16));
        labelSaldo = new JLabel("Saldo Final: R$ 0,00", SwingConstants.CENTER);
        labelSaldo.setFont(new Font("Arial", Font.BOLD, 18));

        painelResultados.add(labelReceitas);
        painelResultados.add(labelDespesas);
        painelResultados.add(labelSaldo);

        add(painelFiltros, BorderLayout.NORTH);
        add(painelResultados, BorderLayout.CENTER);

        setVisible(true);
        SwingUtilities.invokeLater(this::atualizarResumo);
    }

    private void atualizarResumo() {
        String dataInicio = campoDataInicio.getText().trim();
        String dataFim = campoDataFim.getText().trim();

        if (dataInicio.contains("_") || dataFim.contains("_")) {
            JOptionPane.showMessageDialog(this, "Preencha todas as datas corretamente no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double receitas = controller.calcularTotalReceitas(dataInicio, dataFim);

        double despesas = controller.calcularTotalDespesas(dataInicio, dataFim);

        double saldo = receitas - despesas;

        labelReceitas.setText("Total de Receitas: R$ " + String.format("%.2f", receitas));
        labelDespesas.setText("Total de Despesas: R$ " + String.format("%.2f", despesas));
        labelSaldo.setText("Saldo Final: R$ " + String.format("%.2f", saldo));
    }

    private MaskFormatter createMaskFormatter(String format) {
        try {
            MaskFormatter formatter = new MaskFormatter(format);
            formatter.setPlaceholderCharacter('_');
            return formatter;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
