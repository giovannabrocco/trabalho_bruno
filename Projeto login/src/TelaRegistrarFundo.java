import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;




public class TelaRegistrarFundo extends JFrame {



 private JTextField campoValor;
 private TelaPrincipal telaPrincipal;
 private String emailUsuario;





public TelaRegistrarFundo(TelaPrincipal telaPrincipal, String emailUsuario) {


   this.telaPrincipal = telaPrincipal;


  this.emailUsuario = emailUsuario;



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
            double valor = Double.parseDouble(campoValor.getText().replace(",", "."));



      if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }


      telaPrincipal.atualizarValorDisplay(valor);



       Path caminhoFundo = Paths.get("usuarios", emailUsuario, "fundos.txt");
            Files.createDirectories(caminhoFundo.getParent());



        try (BufferedWriter writer = Files.newBufferedWriter(caminhoFundo, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write(String.format("%.2f", valor));
                writer.newLine();
            }



      JOptionPane.showMessageDialog(this, "Valor registrado com sucesso!");
            dispose();

   } catch (NumberFormatException ex) {


     JOptionPane.showMessageDialog(this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE);


    } catch (IOException ex) {


            JOptionPane.showMessageDialog(this, "Erro ao salvar o valor: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);












        }
    }
}
