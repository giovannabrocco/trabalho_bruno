package view;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class TelaResumoFinanceiroView extends JFrame {
    private final JFormattedTextField campoDataInicio;
    private final JFormattedTextField campoDataFim;
    private final JLabel labelReceitas;
    private final JLabel labelDespesas;
    private final JLabel labelSaldo;
    private final JButton btnFiltrar;

    public TelaResumoFinanceiroView() {
        setTitle("Resumo Financeiro");
        setFont(new Font("Century Gothic", Font.BOLD, 18));
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

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar. setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnFiltrar.setBackground(Color.BLACK);
        btnFiltrar.setForeground(Color.WHITE);
        painelFiltros.add(btnFiltrar);

        JPanel painelResultados = new JPanel(new GridLayout(3, 1, 10, 10));
        painelResultados.setBackground(Color.WHITE);
        painelResultados.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        labelReceitas = new JLabel("Total de Receitas: R$ 0,00", SwingConstants.CENTER);
        labelReceitas.setFont(new Font("Century Gothic", Font.BOLD, 16));
        labelDespesas = new JLabel("Total de Despesas: R$ 0,00", SwingConstants.CENTER);
        labelDespesas.setFont(new Font("Century Gothic", Font.BOLD, 16));
        labelSaldo = new JLabel("Saldo Final: R$ 0,00", SwingConstants.CENTER);
        labelSaldo.setFont(new Font("Century Gothic", Font.BOLD, 18));

        painelResultados.add(labelReceitas);
        painelResultados.add(labelDespesas);
        painelResultados.add(labelSaldo);

        add(painelFiltros, BorderLayout.NORTH);
        add(painelResultados, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setFiltrarListener(ActionListener listener) {
        btnFiltrar.addActionListener(listener);
    }

    public String getDataInicio() {
        return campoDataInicio.getText().trim();
    }

    public String getDataFim() {
        return campoDataFim.getText().trim();
    }

    public void atualizarResumo(double receitas, double despesas, double saldo) {
        labelReceitas.setText("Total de Receitas: R$ " + String.format("%.2f", receitas));
        labelDespesas.setText("Total de Despesas: R$ " + String.format("%.2f", despesas));
        labelSaldo.setText("Saldo Final: R$ " + String.format("%.2f", saldo));
    }

    public void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
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
