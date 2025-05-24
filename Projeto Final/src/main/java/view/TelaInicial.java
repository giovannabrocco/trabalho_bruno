package view;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        setTitle("Tela Inicial");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE);

        // Painel que contém os botões
        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 10, 10));
        painelBotoes.setBackground(Color.WHITE);

        JButton botaoLogin = new JButton("Login");
        JButton botaoCadastro = new JButton("Cadastrar-se");

        configurarBotao(botaoLogin);
        configurarBotao(botaoCadastro);

        botaoLogin.addActionListener(e -> {
            new TelaLogin();
            dispose();
        });

        botaoCadastro.addActionListener(e -> {
            new TelaCadastro();
            dispose();
        });

        painelBotoes.add(botaoLogin);
        painelBotoes.add(botaoCadastro);

        // Centraliza o painel com os botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(painelBotoes, gbc);

        setVisible(true);
    }

    private void configurarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(150, 40));
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}
