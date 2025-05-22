
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.UsuarioDAO;
import model.Usuario;

public class TelaCadastro extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha, campoConfirmarSenha;
    private JButton botaoCadastrar, botaoVoltar;

    public TelaCadastro() {
        setTitle("Cadastro");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        campoConfirmarSenha = new JPasswordField();
        botaoCadastrar = new JButton("Cadastrar");
        botaoVoltar = new JButton("Voltar");

        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(new JLabel("Confirmar Senha:"));
        add(campoConfirmarSenha);
        add(botaoCadastrar);
        add(botaoVoltar);

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());
                String confirmarSenha = new String(campoConfirmarSenha.getPassword());

                if (!senha.equals(confirmarSenha)) {
                    JOptionPane.showMessageDialog(null, "As senhas não coincidem.");
                    return;
                }

                UsuarioDAO dao = new UsuarioDAO();
                if (dao.buscarPorEmail(email) != null) {
                    JOptionPane.showMessageDialog(null, "Usuário já existe.");
                    return;
                }

                Usuario usuario = new Usuario(email, senha);
                dao.salvar(usuario);

                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                dispose();
                new TelaLogin().setVisible(true);
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaLogin().setVisible(true);
            }
        });
    }
}
