package view;

import controller.CategoriaFinanceiraController;
import model.CategoriaFinanceira;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import java.awt.*;

public class CategoriaFinanceiraView extends JFrame {
    private JTextField txtNome;
    private JButton btnAdicionar, btnExcluir;
    private JTable table;
    private DefaultTableModel model;
    private CategoriaFinanceiraController controller;

    public CategoriaFinanceiraView() {
        this.controller = new CategoriaFinanceiraController();

        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("TableHeader.font", new Font("Arial", Font.BOLD, 14));

        setTitle("Categorias Financeiras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Gerenciar Categoria");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelTop.add(titulo);

        JPanel linhaFormulario = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        txtNome = new JTextField(20);
        btnAdicionar = new JButton("Adicionar");
        btnExcluir = new JButton("Deletar");
        estilizarBotao(btnAdicionar, false);
        estilizarBotao(btnExcluir, true);

        linhaFormulario.add(new JLabel("Nome:"));
        linhaFormulario.add(txtNome);
        linhaFormulario.add(btnAdicionar);
        linhaFormulario.add(btnExcluir);
        panelTop.add(linhaFormulario);
        add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Categoria"}, 0);
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Ações
        btnAdicionar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            if (!nome.isEmpty()) {
                controller.adicionarCategoria(nome);
                carregarTabela();
                txtNome.setText("");
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha >= 0) {
                int id = (int) model.getValueAt(linha, 0); // Pega o ID da categoria
                controller.excluirCategoria(id);           // Corrige aqui
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
            }
        });


        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                JOptionPane.showMessageDialog(this, "Edição direta desativada nesta versão.");
                carregarTabela();
            }
        });

        carregarTabela();
        setVisible(true);
    }

    private void estilizarBotao(JButton botao, boolean isDelete) {
        Color cor = new Color(0, 0, 0);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setPreferredSize(new Dimension(100, 30));
    }

    private void carregarTabela() {
        model.setRowCount(0);
        for (CategoriaFinanceira cat : controller.listarCategorias()) {
            model.addRow(new Object[]{cat.getId(), cat.getNome()});
        }
    }



}
