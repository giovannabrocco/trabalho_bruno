package view;

import controller.HistoricoFinanceiroController;
import dao.CategoriaFinanceiraDAO;
import model.CategoriaFinanceira;

import model.Transacao;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.List;



public class TelaVerHistorico extends JFrame {
    private CategoriaFinanceiraDAO categoriaDAO = new CategoriaFinanceiraDAO();

    private JLabel labelTotal;
    private DefaultTableModel tabelaModel;
    private JFormattedTextField campoData;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboTipo;
    private HistoricoFinanceiroController controller = new HistoricoFinanceiroController();

    public TelaVerHistorico() {
        setTitle("Histórico de Finanças");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltros.setBackground(Color.WHITE);
        campoData = new JFormattedTextField(createMaskFormatter("##/##/####"));
        campoData.setColumns(10);

        comboCategoria = new JComboBox<>();
        carregarCategorias();

        comboTipo = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(Color.BLACK);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> carregarHistorico());

        painelFiltros.add(new JLabel("Data (dd/MM/yyyy):"));
        painelFiltros.add(campoData);
        painelFiltros.add(new JLabel("Categoria:"));
        painelFiltros.add(comboCategoria);
        painelFiltros.add(new JLabel("Classificação:"));
        painelFiltros.add(comboTipo);
        painelFiltros.add(btnFiltrar);
        add(painelFiltros, BorderLayout.NORTH);

        String[] colunas = {"Valor (R$)", "Tipo", "Categoria", "Descrição", "Data"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(tabelaModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(new TitledBorder("Últimas Ações"));
        add(scrollPane, BorderLayout.CENTER);

        labelTotal = new JLabel("Total filtrado: R$ 0,00", SwingConstants.RIGHT);
        labelTotal.setFont(new Font("Arial", Font.BOLD, 14));
        labelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(labelTotal, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarCategorias() {
        comboCategoria.removeAllItems();
        comboCategoria.addItem("Todas");
        List<CategoriaFinanceira> categorias = categoriaDAO.listar();
        for (CategoriaFinanceira cat : categorias) {
            comboCategoria.addItem(cat.getNome());
        }
    }


    private void carregarHistorico() {
        tabelaModel.setRowCount(0);

        String dataFiltro = campoData.getText().trim().replace("_", "");
        String tipoFiltro = comboTipo.getSelectedItem().toString();
        String categoriaFiltro = comboCategoria.getSelectedItem().toString();

        List<Transacao> transacoes = controller.filtrarTransacoes(
                dataFiltro.isEmpty() ? null : dataFiltro,
                tipoFiltro,
                categoriaFiltro
        );

        if (transacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum registro encontrado para o filtro aplicado.");
        }

        double total = 0;
        for (Transacao t : transacoes) {
            total += t.getValor();
            String valorFormatado = String.format("R$ %.2f", t.getValor());
            String tipoFormatado = t.getTipo().substring(0, 1).toUpperCase() + t.getTipo().substring(1);
            tabelaModel.addRow(new Object[]{valorFormatado, tipoFormatado, t.getCategoria(), t.getDescricao(), t.getData()});
        }

        labelTotal.setText(String.format("Total filtrado: R$ %.2f", total));
    }

    private MaskFormatter createMaskFormatter(String format) {
        try {
            MaskFormatter formatter = new MaskFormatter(format);
            formatter.setPlaceholderCharacter('_');
            return formatter;
        } catch (ParseException e) {
            System.err.println("Erro ao criar máscara de data: " + e.getMessage());
            return null;
        }
    }
}
