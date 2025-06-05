package view;
import model.Transacao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaVerHistoricoView extends JFrame {

    private JTable tabelaHistorico;
    private DefaultTableModel tabelaModel;
    private JButton btnFiltrar;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboCategoria;
    private JTextField campoDataInicio;
    private JTextField campoDataFim;

    public TelaVerHistoricoView() {
        setTitle("Histórico Financeiro");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);


        JPanel painelFiltros = new JPanel();
        painelFiltros.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        painelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));
        painelFiltros.setBackground(Color.WHITE);

        painelFiltros.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[] {"", "Receita", "Despesa"});
        comboTipo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        painelFiltros.add(comboTipo);

        painelFiltros.add(new JLabel("Categoria:"));
        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("");
        painelFiltros.add(comboCategoria);

        painelFiltros.add(new JLabel("Data Início (dd/MM/yyyy):"));
        campoDataInicio = new JTextField(8);
        painelFiltros.add(campoDataInicio);

        painelFiltros.add(new JLabel("Data Fim (dd/MM/yyyy):"));
        campoDataFim = new JTextField(8);
        painelFiltros.add(campoDataFim);



        btnFiltrar = new JButton("Filtrar");
        painelFiltros.add(btnFiltrar);

        add(painelFiltros, BorderLayout.NORTH);




        String[] colunas = {"Valor", "Tipo", "Categoria", "Descrição", "Data"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaHistorico = new JTable(tabelaModel);
        tabelaHistorico.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public String getTipoFiltro() {
        return (String) comboTipo.getSelectedItem();
    }

    public String getCategoriaFiltro() {
        return (String) comboCategoria.getSelectedItem();
    }

    public String getDataInicioFiltro() {
        return campoDataInicio.getText().trim();
    }

    public String getDataFimFiltro() {
        return campoDataFim.getText().trim();
    }

    public void setCategorias(List<String> categorias) {
        comboCategoria.removeAllItems();
        comboCategoria.addItem("");
        for (String c : categorias) {
            comboCategoria.addItem(c);
        }
    }




    public void atualizarTabela(List<Transacao> transacoes) {
        tabelaModel.setRowCount(0);
        for (Transacao t : transacoes) {
            tabelaModel.addRow(new Object[]{
                    String.format("R$ %.2f", t.getValor()),
                    t.getTipo(),
                    t.getCategoria(),
                    t.getDescricao(),
                    t.getData()
            });
        }
    }

    public void adicionarListenerFiltrar(ActionListener listener) {
        btnFiltrar.addActionListener(listener);
    }

    public void mostrarMensagemErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
