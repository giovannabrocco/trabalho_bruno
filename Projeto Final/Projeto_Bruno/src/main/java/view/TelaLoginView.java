package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaLoginView extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;

    public TelaLoginView() {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setFont(new Font("Century Gothic", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelEmail, gbc);

        campoEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(campoEmail, gbc);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Century Gothic", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSenha, gbc);

        campoSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(campoSenha, gbc);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Century Gothic", Font.BOLD, 15));
        btnEntrar.setBackground(Color.BLACK);
        btnEntrar.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(btnEntrar, gbc);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Century Gothic", Font.BOLD, 15));
        btnCadastrar.setBackground(Color.BLACK);
        btnCadastrar.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(btnCadastrar, gbc);


    }

    public String getEmail() {
        return campoEmail.getText().trim();
    }

    public String getSenha() {
        return new String(campoSenha.getPassword());
    }

    public void adicionarListenerEntrar(ActionListener listener) {

        for (ActionListener al : btnEntrar.getActionListeners()) {
            btnEntrar.removeActionListener(al);
        }
        btnEntrar.addActionListener(listener);
    }

    public void adicionarListenerCadastrar(ActionListener listener) {

        for (ActionListener al : btnCadastrar.getActionListeners()) {
            btnCadastrar.removeActionListener(al);
        }
        btnCadastrar.addActionListener(listener);
    }


    public void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro de Login", JOptionPane.ERROR_MESSAGE);
    }
}

