package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.regex.*;
import javax.swing.text.*;
import controller.UsuarioController;
import model.Usuario;

public class TelaCadastro extends JFrame {
    private JTextField campoNome, campoEmail;
    private JPasswordField campoSenha, campoConfirmarSenha;
    private JFormattedTextField campoTelefone, campoCpf, campoDataNascimento;
    private JButton botaoCadastrar, botaoLogin;
    private JLabel avisoSenha, avisoEmail;
    private UsuarioController usuarioController;

    public TelaCadastro() {
        usuarioController = new UsuarioController();

        setTitle("Cadastro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font fonte = new Font("Arial", Font.BOLD, 16);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(Color.WHITE);
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Preencha seus dados"));

        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField(25);
        avisoEmail = new JLabel("");
        avisoEmail.setForeground(Color.RED);
        avisoEmail.setFont(new Font("Arial", Font.BOLD, 14));

        campoEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String email = campoEmail.getText().trim();
                if (!email.isEmpty() && !validarEmail(email)) {
                    avisoEmail.setText("⚠ Email inválido");
                } else {
                    avisoEmail.setText("");
                }
            }
        });

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(25);

        JLabel labelConfirmarSenha = new JLabel("Confirmar Senha:");
        campoConfirmarSenha = new JPasswordField(25);
        avisoSenha = new JLabel("");
        avisoSenha.setForeground(Color.RED);
        avisoSenha.setFont(new Font("Arial", Font.BOLD, 14));

        campoConfirmarSenha.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarSenha();
            }
        });

        JCheckBox checkMostrarSenha = new JCheckBox("Mostrar senha");
        checkMostrarSenha.setBackground(Color.WHITE);
        checkMostrarSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        checkMostrarSenha.addActionListener(e -> {
            if (checkMostrarSenha.isSelected()) {
                campoSenha.setEchoChar((char) 0);
                campoConfirmarSenha.setEchoChar((char) 0);
            } else {
                campoSenha.setEchoChar('•');
                campoConfirmarSenha.setEchoChar('•');
            }
        });

        JLabel labelNome = new JLabel("Nome:");
        campoNome = new JTextField(25);

        JLabel labelTelefone = new JLabel("Telefone:");
        campoTelefone = new JFormattedTextField(createMaskFormatter("(##) #####-####"));

        JLabel labelCpf = new JLabel("CPF:");
        campoCpf = new JFormattedTextField(createMaskFormatter("###.###.###-##"));

        JLabel labelDataNascimento = new JLabel("Data de Nascimento:");
        campoDataNascimento = new JFormattedTextField(createMaskFormatter("##/##/####"));

        botaoCadastrar = new JButton("Cadastrar");
        botaoLogin = new JButton("Login");

        configurarBotao(botaoCadastrar);
        configurarBotao(botaoLogin);

        int y = 0;
        adicionarCampo(painelFormulario, gbc, labelEmail, campoEmail, y++);
        gbc.gridx = 1; gbc.gridy = y++;
        painelFormulario.add(avisoEmail, gbc);
        adicionarCampo(painelFormulario, gbc, labelSenha, campoSenha, y++);
        adicionarCampo(painelFormulario, gbc, labelConfirmarSenha, campoConfirmarSenha, y++);
        gbc.gridx = 1; gbc.gridy = y++;
        painelFormulario.add(avisoSenha, gbc);
        gbc.gridx = 1; gbc.gridy = y++;
        painelFormulario.add(checkMostrarSenha, gbc);
        adicionarCampo(painelFormulario, gbc, labelNome, campoNome, y++);
        adicionarCampo(painelFormulario, gbc, labelTelefone, campoTelefone, y++);
        adicionarCampo(painelFormulario, gbc, labelCpf, campoCpf, y++);
        adicionarCampo(painelFormulario, gbc, labelDataNascimento, campoDataNascimento, y++);

        gbc.gridx = 0; gbc.gridy = y;
        painelFormulario.add(botaoCadastrar, gbc);
        gbc.gridx = 1;
        painelFormulario.add(botaoLogin, gbc);

        add(painelFormulario);

        botaoCadastrar.addActionListener(e -> cadastrarUsuario());
        botaoLogin.addActionListener(e -> {
            new TelaLogin();
            dispose();
        });

        campoEmail.requestFocusInWindow();
        setVisible(true);
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, JLabel label, JComponent campo, int linha) {
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(label, gbc);
        gbc.gridx = 1;
        painel.add(campo, gbc);
    }

    private void configurarBotao(JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        botao.setPreferredSize(new Dimension(180, 40));
    }

    private MaskFormatter createMaskFormatter(String format) {
        try {
            MaskFormatter formatter = new MaskFormatter(format);
            formatter.setValidCharacters("0123456789");
            formatter.setPlaceholderCharacter('_');
            return formatter;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void verificarSenha() {
        String senha = new String(campoSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());
        avisoSenha.setText(!senha.equals(confirmarSenha) ? "⚠ As senhas não são iguais!" : "");
    }

    private void cadastrarUsuario() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());
        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().replaceAll("[()\\s-]", "");
        String cpf = campoCpf.getText().replaceAll("[.-]", "");
        String dataNascimento = campoDataNascimento.getText().trim();

        if (email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()
                || nome.isEmpty() || telefone.contains("_") || cpf.contains("_") || dataNascimento.contains("_")) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um email válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario(email, senha, nome, telefone, cpf, dataNascimento);
        boolean sucesso = usuarioController.cadastrarUsuario(novoUsuario);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário. Email já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoEmail.setText("");
        campoSenha.setText("");
        campoConfirmarSenha.setText("");
        campoNome.setText("");
        campoTelefone.setValue(null);
        campoCpf.setValue(null);
        campoDataNascimento.setValue(null);
        campoEmail.requestFocus();
    }

    private boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public static void main(String[] args) {
        new TelaCadastro();
    }
}
