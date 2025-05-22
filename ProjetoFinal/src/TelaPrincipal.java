import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import model.CategoriaFinanceira;
import model.Transacao;
import model.Usuario;
import dao.CategoriaFinanceiraDAO;

public class TelaPrincipal extends JFrame {

    private JLabel displayValor;
    private DefaultTableModel tabelaModel;
    private Usuario usuario;
    private Financeiro financeiro;

    public TelaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        this.financeiro = new Financeiro(usuario);

        setTitle("Tela Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout());
        displayValor = new JLabel("Saldo: R$ " + financeiro.calcularSaldo());
        painelTopo.add(displayValor);

        JPanel painelTransacoes = new JPanel(new BorderLayout());
        painelTransacoes.setBorder(new TitledBorder("Transações"));

        tabelaModel = new DefaultTableModel(new Object[]{"Descrição", "Valor", "Data", "Tipo"}, 0);
        JTable tabela = new JTable(tabelaModel);
        painelTransacoes.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Adicionar Transação");
        painelTopo.add(btnAdd);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String descricao = JOptionPane.showInputDialog("Descrição:");
                double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
                String tipo = JOptionPane.showInputDialog("Tipo (Receita/Despesa):");

                CategoriaFinanceiraDAO catDAO = new CategoriaFinanceiraDAO();
                CategoriaFinanceira categoria = catDAO.buscarTodos(usuario).isEmpty()
                        ? null
                        : catDAO.buscarTodos(usuario).get(0);

                Transacao nova = new Transacao(tipo,
                        categoria != null ? categoria.getNome() : "Padrão",
                        descricao,
                        LocalDate.now().toString(),
                        valor);

                nova.setUsuario(usuario);
                financeiro.adicionarTransacao(nova);
                atualizarTabela();
            }
        });

        add(painelTopo, BorderLayout.NORTH);
        add(painelTransacoes, BorderLayout.CENTER);

        atualizarTabela();
    }

    private void atualizarTabela() {
        tabelaModel.setRowCount(0);
        for (Transacao t : financeiro.getTransacoes()) {
            tabelaModel.addRow(new Object[]{t.getDescricao(), t.getValor(), t.getDataTexto(), t.getTipo()});
        }
        displayValor.setText("Saldo: R$ " + financeiro.calcularSaldo());
    }

    public void recarregarSaldoEDisplay() {
        atualizarTabela();
    }

    public void atualizarTabelaUltimosRegistros() {
        atualizarTabela();
    }
}
