import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TelaVerHistorico extends JFrame {
    private DefaultTableModel tabelaModel;

    public TelaVerHistorico() {
        setTitle("Histórico de Finanças");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurar tabela
        String[] colunas = {"Valor (R$)", "Categoria", "Descrição", "Data"};
        tabelaModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tabelaModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(new TitledBorder("Últimas Ações"));
        add(scrollPane, BorderLayout.CENTER);

        carregarHistorico();
        setVisible(true);
    }

    private void carregarHistorico() {
        try {
            List<String> linhas = Files.readAllLines(Paths.get("historico_financas.txt"));
            tabelaModel.setRowCount(0); // Limpar tabela antes de adicionar novos dados
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length == 4) {
                    tabelaModel.addRow(partes);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
