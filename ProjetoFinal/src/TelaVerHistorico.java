
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.HistoricoFinanceiroDAO;
import model.HistoricoFinanceiro;
import model.Usuario;

public class TelaVerHistorico extends JFrame {

    private DefaultTableModel tabelaModel;
    private HistoricoFinanceiroDAO dao;
    private Usuario usuario;

    public TelaVerHistorico(Usuario usuario) {
        this.usuario = usuario;
        this.dao = new HistoricoFinanceiroDAO();

        setTitle("Histórico de Ações");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tabelaModel = new DefaultTableModel(new Object[]{"Ação", "Data/Hora"}, 0);
        JTable tabela = new JTable(tabelaModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarHistorico();
    }

    private void carregarHistorico() {
        tabelaModel.setRowCount(0);
        List<HistoricoFinanceiro> historico = dao.buscarTodos(usuario);
        for (HistoricoFinanceiro h : historico) {
            tabelaModel.addRow(new Object[]{h.getAcao(), h.getDataHora()});
        }
    }
}
