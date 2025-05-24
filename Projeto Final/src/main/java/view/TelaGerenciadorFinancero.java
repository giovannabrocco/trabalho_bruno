package view;

import controller.TransacaoController;
import model.Transacao;
import model.CategoriaFinanceira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import dao.CategoriaFinanceiraDAO;

public class TelaGerenciadorFinancero {
    private TransacaoController transacaoController;
    private CategoriaFinanceiraDAO categoriaDAO = new CategoriaFinanceiraDAO();

    private JFrame frame;
    private JTextField campoDescricao;
    private JTextField campoValor;
    private JTextField campoData;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboCategoria;
    private Interface telaPrincipal;

    private boolean categoriaViewAberta = false;
    private boolean atualizandoCombo = false;


    public TelaGerenciadorFinancero(Interface telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        this.transacaoController = new TransacaoController();

        frame = new JFrame("Gerenciador Financeiro");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        Font fonteGeral = new Font("Arial", Font.PLAIN, 14);

        JLabel lblTitulo = new JLabel("Gerencie suas finanças aqui!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Descrição:"), gbc);
        campoDescricao = new JTextField(20);
        campoDescricao.setFont(fonteGeral);
        gbc.gridx = 1;
        frame.add(campoDescricao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Valor:"), gbc);
        campoValor = new JTextField(20);
        campoValor.setFont(fonteGeral);
        gbc.gridx = 1;
        frame.add(campoValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Data (dd/mm/yyyy):"), gbc);
        try {
            javax.swing.text.MaskFormatter maskData = new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
            campoData = new JFormattedTextField(maskData);
            campoData.setColumns(20);
            campoData.setFont(fonteGeral);
        } catch (java.text.ParseException ex) {
            campoData = new JFormattedTextField(); // fallback
            campoData.setFont(fonteGeral);
            ex.printStackTrace();
        }

        campoData.setFont(fonteGeral);
        gbc.gridx = 1;
        frame.add(campoData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Categoria:"), gbc);
        comboCategoria = new JComboBox<>();
        carregarCategorias();
        comboCategoria.setFont(fonteGeral);
        comboCategoria.addActionListener(e -> {
            if (atualizandoCombo) return;

            Object selecionado = comboCategoria.getSelectedItem();
            if (selecionado != null && selecionado.equals("+Editar Categorias") && !categoriaViewAberta) {
                categoriaViewAberta = true;

                SwingUtilities.invokeLater(() -> {
                    JFrame categoriaFrame = new CategoriaFinanceiraView();
                    categoriaFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            categoriaViewAberta = false;
                            carregarCategorias();
                        }

                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            categoriaViewAberta = false;
                            carregarCategorias();
                        }
                    });
                });
            }
        });




        gbc.gridx = 1;
        frame.add(comboCategoria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(new JLabel("Tipo:"), gbc);
        comboTipo = new JComboBox<>(new String[]{"receita", "despesa"});
        comboTipo.setFont(fonteGeral);
        gbc.gridx = 1;
        frame.add(comboTipo, gbc);

        JButton btnAdicionar = new JButton("Adicionar Transação");
        btnAdicionar.setBackground(Color.BLACK);
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setPreferredSize(new Dimension(200, 30));
        btnAdicionar.addActionListener(this::adicionarTransacao);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnAdicionar, gbc);

        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void carregarCategorias() {
        atualizandoCombo = true;
        comboCategoria.removeAllItems();

        List<CategoriaFinanceira> categorias = categoriaDAO.listar();
        for (CategoriaFinanceira cat : categorias) {
            comboCategoria.addItem(cat.getNome());
        }
        comboCategoria.addItem("+Editar Categorias");

        comboCategoria.setSelectedIndex(categorias.isEmpty() ? -1 : 0);
        atualizandoCombo = false;
    }


    private void adicionarTransacao(ActionEvent e) {
        String descricao = campoDescricao.getText();
        String valorTexto = campoValor.getText();
        String data = campoData.getText();
        String tipo = comboTipo.getSelectedItem().toString();
        String categoria = comboCategoria.getSelectedItem().toString();

        double valor;
        try {
            valor = Double.parseDouble(valorTexto.replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira um valor numérico válido.");
            return;
        }

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(frame, "A data deve estar no formato dd/mm/aaaa.");
            return;
        }

        Transacao transacao = new Transacao(tipo, categoria, descricao, valor, data);
        boolean sucesso = transacaoController.adicionarTransacao(transacao);

        if (sucesso) {
            JOptionPane.showMessageDialog(frame, "Transação registrada com sucesso!");
            campoDescricao.setText("");
            campoValor.setText("");
            campoData.setText("");

            if (telaPrincipal != null) {
                telaPrincipal.recarregarSaldoEDisplay();
                telaPrincipal.atualizarTabelaUltimosRegistros();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Erro ao registrar transação.");
        }
    }
}
