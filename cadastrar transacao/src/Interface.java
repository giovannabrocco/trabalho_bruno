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
    frame.setLayout(new GridBagLayout());
     GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(8, 8, 8, 8);
     Font fonteGeral = new Font("Arial", Font.PLAIN, 14);



       JLabel lblTitulo = new JLabel("Gerencie suas finanças aqui!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));



        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;



        frame.add(new JLabel("Descrição:"), gbc);

    campoDescricao = new JTextField(20);
    campoDescricao.setFont(fonteGeral);
   gbc.gridx = 1;
   frame.add(campoDescricao, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Valor:"), gbc);

    campoValor = new JTextField(20);
  campoValor.setFont(fonteGeral);
 gbc.gridx = 1;
 frame.add(campoValor, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Data (dd/mm/yyyy):"), gbc);

        campoData = new JTextField(20);
        campoData.setFont(fonteGeral);
        gbc.gridx = 1;
        frame.add(campoData, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Tipo:"), gbc);



     comboTipo = new JComboBox<>(new String[]{"receita", "despesa"});
      comboTipo.setFont(fonteGeral);
      gbc.gridx = 1;
      frame.add(comboTipo, gbc);


 JButton btnAdicionar = new JButton("Adicionar Transação");
   btnAdicionar.setBackground(Color.BLACK);
   btnAdicionar.setForeground(Color.WHITE);
     btnAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
     btnAdicionar.setFocusPainted(false);
   btnAdicionar.setPreferredSize(new Dimension(200, 30));




        btnAdicionar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
                   adicionarTransacao(e);
            }
            });



         gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnAdicionar, gbc);
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


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
