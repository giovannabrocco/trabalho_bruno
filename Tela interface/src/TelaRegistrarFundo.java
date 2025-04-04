import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


//to inventando de colocar um display na tela aqui,provavelmente vou me arrepender


public class TelaRegistrarFundo extends JFrame {

 private JTextField campoValor;

 private TelaPrincipal telaPrincipal;



public TelaRegistrarFundo(TelaPrincipal telaPrincipal) {


this.telaPrincipal = telaPrincipal;



setTitle("Registrar Fundo");
setSize(300, 150);
 setLocationRelativeTo(null);
  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
 setLayout(new GridLayout(2, 2, 10, 10));




 campoValor = new JTextField();

 JButton botaoRegistrar = new JButton("Registrar");

 botaoRegistrar.addActionListener(this::registrarValor);

        add(new JLabel("Valor a adicionar (R$):"));
        add(campoValor);
        add(botaoRegistrar);

        setVisible(true);
    }




    private void registrarValor(ActionEvent e) {
        try {
            // Verificando se o valor inserido e valido


    double valor = Double.parseDouble(campoValor.getText().replace(",", "."));

      if (valor <= 0) {
      JOptionPane.showMessageDialog(this, "O valor deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
       return;
        }

      // display da tela principal atualiza aqui

      telaPrincipal.atualizarValorDisplay(valor);


      JOptionPane.showMessageDialog(this, "Valor registrado com sucesso!");
      dispose(); //telinha fecha



 } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

