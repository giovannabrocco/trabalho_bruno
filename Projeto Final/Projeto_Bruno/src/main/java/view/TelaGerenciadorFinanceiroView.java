package view;
import controller.CategoriaFinanceiraController;
import javax.swing.*;
import javax.swing.text.MaskFormatter; // Import MaskFormatter
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.text.ParseException; // Import ParseException
import java.util.List;

public class TelaGerenciadorFinanceiroView extends JFrame {
    private JTextField campoDescricao, campoValor;
    private JFormattedTextField campoData; // Changed to JFormattedTextField
    private JComboBox<String> comboTipo, comboCategoria;
    private JButton btnAdicionar;

    public TelaGerenciadorFinanceiroView(InterfaceView telaPrincipal) {
        setTitle("Gerenciador Financeiro");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteGeral = new Font("Century Gothic", Font.PLAIN, 14);

        JLabel lblTitulo = new JLabel("Gerenciador de Transações", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Century Gothic", Font.BOLD, 16));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Descrição:"), gbc);
        campoDescricao = new JTextField(20);
        campoDescricao.setFont(fonteGeral);
        gbc.gridx = 1;
        campoDescricao.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        add(campoDescricao, gbc);

        // Valor
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Valor:"), gbc);
        campoValor = new JTextField(20);
        campoValor.setFont(fonteGeral);
        gbc.gridx = 1;
        add(campoValor, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Data (dd/mm/yyyy):"), gbc);
        campoData = createFormattedField("##/##/####");
        campoData.setFont(fonteGeral);
        campoData.setColumns(10);
        gbc.gridx = 1;
        add(campoData, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Tipo:"), gbc);
        comboTipo = new JComboBox<>(new String[] { "Receita", "Despesa" });
        comboTipo.setFont(fonteGeral);
        gbc.gridx = 1;
        add(comboTipo, gbc);

        // Categoria
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Categoria:"), gbc);
        comboCategoria = new JComboBox<>();
        comboCategoria.setFont(fonteGeral);
        gbc.gridx = 1;
        add(comboCategoria, gbc);

        // Botão Adicionar
        btnAdicionar = new JButton("Adicionar Transação");
        btnAdicionar.setBackground(Color.BLACK);
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnAdicionar.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAdicionar, gbc);
    }


    private JFormattedTextField createFormattedField(String mask) {
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(formatter);
        } catch (ParseException e) {
            e.printStackTrace();

            return new JFormattedTextField(); 
        }
    }




    public String getDescricao() { return campoDescricao.getText().trim(); }
    public String getValor() { return campoValor.getText().trim(); }
    public String getData() {
        return campoData.getText().trim(); 
    }


    public String getTipo() { return (String) comboTipo.getSelectedItem(); }
    public String getCategoria() { return (String) comboCategoria.getSelectedItem(); }


    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }


    public void limparCampos() {
        campoDescricao.setText("");
        campoValor.setText("");
        campoData.setValue(null);
        comboTipo.setSelectedIndex(0);
        if (comboCategoria.getItemCount() > 0) {
            if (!"+Editar Categorias".equals(comboCategoria.getItemAt(0)) || comboCategoria.getItemCount() > 1) {
                 comboCategoria.setSelectedIndex(0);
            } else {
                 comboCategoria.setSelectedIndex(-1);
            }
        }
    }





    public void setCategorias(List<String> nomesCategorias) {
        comboCategoria.removeAllItems();
        if (nomesCategorias != null) {
         for (String nome : nomesCategorias) {
          comboCategoria.addItem(nome);
            }
        }
    }


    public void adicionarItemCategoria(String item) {
        comboCategoria.addItem(item);
    }

    public void setComboCategoriaSelectedIndex(int index) {
        if (index >= -1 && index < comboCategoria.getItemCount()) {
         comboCategoria.setSelectedIndex(index);
        }
    }




    public Object getCategoriaSelecionadaNoCombo() {
        return comboCategoria.getSelectedItem();
    }


    public int getComboCategoriaItemCount() {
        return comboCategoria.getItemCount();
    }



    public void adicionarListenerCategoriaComboBox(ActionListener listener) {
        comboCategoria.addActionListener(listener);
    }


    public void adicionarListenerAdicionar(ActionListener listener) {
        btnAdicionar.addActionListener(listener);
    }


    public void abrirJanelaEdicaoCategoriasSwing(WindowAdapter listenerFechamento) {
        SwingUtilities.invokeLater(() -> {
            CategoriaFinanceiraView categoriaFrame = new CategoriaFinanceiraView();
            new CategoriaFinanceiraController(categoriaFrame);
            categoriaFrame.addWindowListener(listenerFechamento);
            categoriaFrame.setVisible(true);
        });
    }
}

