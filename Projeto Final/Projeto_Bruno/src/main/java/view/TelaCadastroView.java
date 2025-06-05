package view;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class TelaCadastroView extends JFrame {
    private JTextField campoNome, campoEmail;
    private JPasswordField campoSenha, campoConfirmarSenha;
    private JFormattedTextField campoTelefone, campoCpf, campoDataNascimento;
    private JButton btnCadastrar, btnVoltar;

    public TelaCadastroView() {

        setTitle("Tela de Cadastro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JLabel labelTitulo = new JLabel("Cadastro", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Century Gothic", Font.BOLD, 20));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(7, 2, 10, 10));
        painelCampos.setBackground(Color.WHITE);
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        painelCampos.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Email:"));
        campoEmail = new JTextField();
        painelCampos.add(campoEmail);

        painelCampos.add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        painelCampos.add(campoSenha);

        painelCampos.add(new JLabel("Confirmar Senha:"));
        campoConfirmarSenha = new JPasswordField();
        painelCampos.add(campoConfirmarSenha);

        painelCampos.add(new JLabel("Telefone:"));
        campoTelefone = createFormattedField("(##) #####-####");
        painelCampos.add(campoTelefone);

        painelCampos.add(new JLabel("CPF:"));
        campoCpf = createFormattedField("###.###.###-##");
        painelCampos.add(campoCpf);

        painelCampos.add(new JLabel("Data Nascimento:"));
        campoDataNascimento = createFormattedField("##/##/####");
        painelCampos.add(campoDataNascimento);

        add(painelCampos, BorderLayout.CENTER);




        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.setBackground(Color.WHITE);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnCadastrar.setBackground(Color.BLACK);
        btnCadastrar.setForeground(Color.WHITE);
        painelBotoes.add(btnCadastrar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnVoltar.setBackground(Color.BLACK);
        btnVoltar.setForeground(Color.WHITE);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
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

    public String getNome() {
        return campoNome.getText().trim();
    }
    public String getEmail() {
        return campoEmail.getText().trim();
    }
    public String getSenha() {
        return new String(campoSenha.getPassword()).trim();
    }
    public String getConfirmarSenha() {
        return new String(campoConfirmarSenha.getPassword()).trim();
    }
    public String getTelefone() {
        return campoTelefone.getText().trim();
    }
    public String getCpf() {
        return campoCpf.getText().trim();
    }
    public String getDataNascimento() {
        return campoDataNascimento.getText().trim();
    }
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
    public void adicionarListenerCadastrar(ActionListener listener) {
        btnCadastrar.addActionListener(listener);
    }
    public void adicionarListenerVoltar(ActionListener listener) {
        btnVoltar.addActionListener(listener);
    }
}
