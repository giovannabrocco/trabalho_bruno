package view;

import controller.UsuarioController;
import util.InicializadorBanco;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoLogin, botaoCadastrar;

    public TelaLogin() {
        setTitle("Login e Cadastro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE);

        Font fonte = new Font("Arial", Font.PLAIN, 18);

        JPanel painelLogin = new JPanel(new GridLayout(3, 2, 10, 20));
        painelLogin.setBackground(Color.WHITE);

        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField(20);

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(20);

        botaoLogin = new JButton("Login");
        botaoCadastrar = new JButton("Cadastrar-se");

        labelEmail.setFont(fonte);
        labelSenha.setFont(fonte);
        campoEmail.setFont(fonte);
        campoSenha.setFont(fonte);
        botaoLogin.setFont(fonte);
        botaoCadastrar.setFont(fonte);

        configurarBotao(botaoLogin);
        configurarBotao(botaoCadastrar);

        botaoLogin.addActionListener(e -> realizarLogin());
        botaoCadastrar.addActionListener(e -> {
            new TelaCadastro();
            dispose();
        });

        painelLogin.add(labelEmail);
        painelLogin.add(campoEmail);
        painelLogin.add(labelSenha);
        painelLogin.add(campoSenha);
        painelLogin.add(botaoLogin);
        painelLogin.add(botaoCadastrar);

        add(painelLogin);
        campoEmail.requestFocusInWindow();
        setVisible(true);
    }

    private void realizarLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioController controller = new UsuarioController();
        boolean sucesso = controller.fazerLogin(email, senha);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            dispose();
            abrirTelaPrincipal();
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos!", "Erro de login", JOptionPane.ERROR_MESSAGE);
            campoSenha.setText("");
        }
    }


    private void configurarBotao(JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(180, 40));
    }

    private void abrirTelaPrincipal() {
        new Interface();
    }

    public static void main(String[] args) {
        InicializadorBanco.inicializar();
        new TelaLogin();
    }
}
