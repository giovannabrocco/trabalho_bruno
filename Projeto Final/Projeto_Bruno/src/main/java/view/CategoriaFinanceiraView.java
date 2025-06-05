package view;
import model.CategoriaFinanceira;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriaFinanceiraView extends JFrame {
    private final JTextField campoNome;
    private final JButton btnSalvar, btnExcluir;
    private final JTable tabela;
    private final DefaultTableModel model;

    public CategoriaFinanceiraView() {
        setTitle("Categorias");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        campoNome = new JTextField(20);
        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnExcluir = new JButton("Excluir");
        btnSalvar.setFont(new Font("Century Gothic", Font.BOLD, 14));
        model = new DefaultTableModel(new Object[]{"ID", "Nome"}, 0);
        tabela = new JTable(model);

        JPanel painelTop = new JPanel();
        painelTop.setFont(new Font("Century Gothic", Font.BOLD, 12));
        painelTop.add(new JLabel("Nome:"));
        painelTop.add(campoNome);
        painelTop.add(btnSalvar);

        add(painelTop, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(btnExcluir, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void addSalvarListener(ActionListener l) {
        btnSalvar.addActionListener(l);
    }

    public void addExcluirListener(ActionListener l) {
        btnExcluir.addActionListener(l);
    }

    public String getCampoNome() {
        return campoNome.getText().trim();
    }

    public int getIdCategoriaSelecionada() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            return (int) model.getValueAt(row, 0);
        }
        return -1;
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public void limparCampos() {
        campoNome.setText("");
    }

    public void preencherTabela(List<CategoriaFinanceira> categorias) {
        model.setRowCount(0);
        for (CategoriaFinanceira c : categorias) {
            model.addRow(new Object[]{c.getId(), c.getNome()});
        }
    }
}
