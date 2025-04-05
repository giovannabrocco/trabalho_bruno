import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Interface {
    private Financeiro financeiro;
    private JFrame frame;
    private JTextField campoDescricao;
    private JTextField campoValor;
    private JTextField campoData;
    private JComboBox<String> comboTipo;
    private JTextField campoPeriodo;  // Campo para o período
    private JButton btnResumo;  // Botão de resumo

    public Interface() {
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

        // Botão "Ver Resumo" (mostrar campo de período)
        btnResumo = new JButton("Ver Resumo");
        btnResumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCampoPeriodo();
            }
        });
        gbc.gridy = 6;
        frame.add(btnResumo, gbc);

        frame.setSize(400, 300);  // Ajustando o tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Criar instância de Financeiro
        financeiro = new Financeiro();  // Agora a instância de Financeiro não depende de nome de usuário
    }

    private void mostrarCampoPeriodo() {
        // Criar o campo de período apenas quando o botão "Ver Resumo" for clicado
        if (campoPeriodo == null) {
            // Cria o campo de período dinamicamente
            campoPeriodo = new JTextField(10);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 7;
            frame.add(new JLabel("Período (mm/yyyy):"), gbc);

            gbc.gridx = 1;
            frame.add(campoPeriodo, gbc);

            // Atualiza a interface gráfica
            frame.revalidate();
            frame.repaint();

            // Atualiza o texto do botão para "Calcular Resumo"
            btnResumo.setText("Calcular Resumo");
        } else {
            // Quando o botão é clicado novamente, calcula o resumo com o período
            calcularResumo();
        }
    }

    private void calcularResumo() {
        String periodo = campoPeriodo.getText().trim();
        if (periodo.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira um período.");
            return;
        }

        // Salvar o resumo financeiro para o período especificado
        financeiro.salvarResumoFinanceiro(periodo);

        try {
            // Abrindo o arquivo 'financas.txt' no Notepad
            Runtime.getRuntime().exec("notepad.exe " + "financas.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao abrir o arquivo no Notepad.");
        }
    }

    private void adicionarTransacao(ActionEvent e) {
        String descricao = campoDescricao.getText();
        String valorTexto = campoValor.getText();
        String data = campoData.getText();
        String tipo = comboTipo.getSelectedItem().toString();

        double valor = 0;
        try {
            valor = Double.parseDouble(valorTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira um valor numérico válido.");
            return;
        }

        Transacao transacao = new Transacao(tipo, descricao, valor, data);
        financeiro.adicionarTransacao(transacao);

        campoDescricao.setText("");
        campoValor.setText("");
        campoData.setText("");
    }
}
