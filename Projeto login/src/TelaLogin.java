

 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;





//aqui eu preciso ver como vou puxar as informaçoes do txt e so as que eu quero, talvez vou ter que usar um filtro para ler so email e senha, num sei como


public class TelaLogin extends JFrame {




 private JTextField campoEmail;

    private JPasswordField campoSenha;

    private JButton botaoLogin, botaoCadastrar;





    private HashMap<String, String> usuarios = new HashMap<>();



 public TelaLogin() {


   setTitle("Login e Cadastro");

        setSize(800, 600);

        //  mesmo tamanho da tela inicial


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridBagLayout()); // layoaut no centro


        getContentPane().setBackground(Color.WHITE);



  Font fonte = new Font("Arial", Font.PLAIN, 18);



        //Botoesss


   JPanel painelLogin = new JPanel(new GridLayout(3, 2, 10, 20));

        painelLogin.setBackground(Color.WHITE);



    JLabel labelEmail = new JLabel("Email:");

        campoEmail = new JTextField(20);

    JLabel labelSenha = new JLabel("Senha:");

        campoSenha = new JPasswordField(20);

        botaoLogin = new JButton("Login");


        botaoCadastrar = new JButton("Cadastrar-se");








        // tamanho das  fontes





   labelEmail.setFont(fonte);
  labelSenha.setFont(fonte);
    campoEmail.setFont(fonte);
  campoSenha.setFont(fonte);
    botaoLogin.setFont(fonte);
    botaoCadastrar.setFont(fonte);







    configurarBotao(botaoLogin);
    configurarBotao(botaoCadastrar);



        //carregando o usuarioo


     carregarUsuarios();




   botaoLogin.addActionListener(e ->
        {


          String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());


            if (usuarios.containsKey(email) && usuarios.get(email).equals(senha)) {

             JOptionPane.showMessageDialog(null, "Login bem-sucedido!");

                dispose();

                abrirTelaPrincipal();


  } else {



  JOptionPane.showMessageDialog(null, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);


            }


        });

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



        setVisible(true);
    }



    private void configurarBotao(JButton botao) {




   botao.setBackground(Color.BLACK);
     botao.setForeground(Color.WHITE);
    botao.setFocusPainted(false);
    botao.setBorderPainted(false);
    botao.setPreferredSize(new Dimension(180, 40));



    }




    private void abrirTelaPrincipal() {



        JFrame telaPrincipal = new JFrame("Bem-vindo");
        telaPrincipal.setSize(400, 200);
        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.add(new JLabel("Bem-vindo ao sistema!", SwingConstants.CENTER), BorderLayout.CENTER);
        telaPrincipal.setVisible(true);



    }










    private void carregarUsuarios() {



    try (BufferedReader reader = new BufferedReader(new FileReader("cadastros.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {


                String[] dados = linha.split(";");

                if (dados.length >= 2) {


                    String email = dados[0];
                    String senha = dados[1];
                    usuarios.put(email, senha);

                }
            }


        } catch (IOException e) {


            JOptionPane.showMessageDialog(null, "Erro ao carregar dados de usuários.", "Erro", JOptionPane.ERROR_MESSAGE);


        }
    }


    public static void main(String[] args) {



  new TelaLogin();




    }
}
