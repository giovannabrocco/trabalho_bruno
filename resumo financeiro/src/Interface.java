import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {
    private Financeiro financeiro;
    private JFrame frame;
    private JTextField campoDescricao;
    private JTextField campoValor;
    private JTextField campoData;
    private JComboBox<String> comboTipo;

    public Interface(Financeiro financeiro) {
        this.financeiro = financeiro;
        frame = new JFrame("Gerenciador Financeiro");
        frame.setLayout(new GridBagLayout());  // Usando GridBagLayout para um controle melhor de alinhamento
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adicionando algum espaço entre os componentes

        // Título: "Gerencie suas finanças aqui!"
        JLabel lblTitulo = new JLabel("Gerencie suas finanças aqui!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;  // Título ocupa 2 colunas
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(lblTitulo, gbc);

        // Rótulo e campo de Descrição
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Descrição:"), gbc);

        campoDescricao = new JTextField(20);
        gbc.gridx = 1;
        frame.add(campoDescricao, gbc);

        // Rótulo e campo de Valor
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Valor:"), gbc);

        campoValor = new JTextField(10);
        gbc.gridx = 1;
        frame.add(campoValor, gbc);

        // Rótulo e campo de Data
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Data (dd/mm/yyyy):"), gbc);

        campoData = new JTextField(10);
        gbc.gridx = 1;
        frame.add(campoData, gbc);

        // Rótulo e combo de Tipo
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Tipo:"), gbc);

        comboTipo = new JComboBox<>(new String[]{"receita", "despesa"});
        gbc.gridx = 1;
        frame.add(comboTipo, gbc);

        // Botão "Adicionar Transação"
        JButton btnAdicionar = new JButton("Adicionar Transação");
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarTransacao(e);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        frame.add(btnAdicionar, gbc);

        // Botão "Ver Resumo"
        JButton btnResumo = new JButton("Ver Resumo");
        btnResumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResumoFinanceiro(financeiro);
            }
        });
        gbc.gridy = 6;
        frame.add(btnResumo, gbc);

        // Ajustando o tamanho da janela
        frame.setSize(400, 300);  // Aumentando o tamanho da interface
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void adicionarTransacao(ActionEvent e) {
        String descricao = campoDescricao.getText();
        String valorTexto = campoValor.getText(); // Obtém o valor como texto
        String data = campoData.getText(); // A data será tratada como texto
        String tipo = comboTipo.getSelectedItem().toString();

        // Validando se o valor é um número válido
        double valor = 0;
        try {
            valor = Double.parseDouble(valorTexto); // Tenta converter para double
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira um valor numérico válido.");
            return; // Se o valor não for válido, não continua a execução
        }

        // Cria a transação e a adiciona ao sistema financeiro
        Transacao transacao = new Transacao(tipo, descricao, valor, data);
        financeiro.adicionarTransacao(transacao);

        // Limpa os campos após adicionar a transação
        campoDescricao.setText("");
        campoValor.setText("");
        campoData.setText("");
    }
}
