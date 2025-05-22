
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.UsuarioDAO;
import model.Usuario;

public class TelaLogin extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoLogin, botaoCadastrar;

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        botaoLogin = new JButton("Login");
        botaoCadastrar = new JButton("Cadastrar");

        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(botaoLogin);
        add(botaoCadastrar);

        botaoLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());

                UsuarioDAO dao = new UsuarioDAO();
                Usuario usuario = dao.buscarPorEmail(email);

                if (usuario != null && usuario.getSenha().equals(senha)) {
                    JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                    dispose();
                    new TelaPrincipal(usuario).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha incorretos.");
                }
            }
        });

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaCadastro().setVisible(true);
            }
        });
    }
}
