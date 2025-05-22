package view;

import dao.CategoriaFinanceiraDAO;
import model.CategoriaFinanceira;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CategoriaFinanceiraView extends JFrame {

    private JTable tabela;
    private DefaultTableModel tabelaModel;
    private CategoriaFinanceiraDAO dao;
    private Usuario usuario;

    public CategoriaFinanceiraView(Usuario usuario) {
        this.usuario = usuario;
        this.dao = new CategoriaFinanceiraDAO();

        setTitle("Gerenciar Categorias");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tabelaModel = new DefaultTableModel(new Object[]{"Nome"}, 0);
        tabela = new JTable(tabelaModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(this::adicionarCategoria);
        btnExcluir.addActionListener(this::excluirCategoria);

        carregarCategorias();
    }

    private void carregarCategorias() {
        tabelaModel.setRowCount(0);
        List<CategoriaFinanceira> categorias = dao.buscarTodos(usuario);
        for (CategoriaFinanceira cat : categorias) {
            tabelaModel.addRow(new Object[]{cat.getNome()});
        }
    }

    private void adicionarCategoria(ActionEvent e) {
        String nome = JOptionPane.showInputDialog("Digite o nome da nova categoria:");
        if (nome != null && !nome.trim().isEmpty()) {
            CategoriaFinanceira nova = new CategoriaFinanceira(nome);
            nova.setUsuario(usuario);
            dao.salvar(nova);
            carregarCategorias();
        }
    }

    private void excluirCategoria(ActionEvent e) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada >= 0) {
            String nome = (String) tabelaModel.getValueAt(linhaSelecionada, 0);
            List<CategoriaFinanceira> categorias = dao.buscarTodos(usuario);
            for (CategoriaFinanceira cat : categorias) {
                if (cat.getNome().equals(nome)) {
                    dao.deletar(cat);
                    break;
                }
            }
            carregarCategorias();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para excluir.");
        }
    }
}
