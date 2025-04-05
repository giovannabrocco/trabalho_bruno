import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;




public class TelaResumoFinanceiro extends JFrame {

    private String emailUsuario;



 public TelaResumoFinanceiro(String emailUsuario) {


        this.emailUsuario = emailUsuario;




  setTitle("Resumo Financeiro");

  setSize(400, 200);

   setLocationRelativeTo(null);


  setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(3, 1, 10, 10));




 double totalFundos = carregarFundos();

 double totalGastos = carregarGastos();

 double saldoFinal = totalFundos - totalGastos;





 add(new JLabel("Total de Fundos: R$ " + String.format("%.2f", totalFundos), SwingConstants.CENTER));

  add(new JLabel("Total de Gastos: R$ " + String.format("%.2f", totalGastos), SwingConstants.CENTER));

     add(new JLabel("Saldo Final: R$ " + String.format("%.2f", saldoFinal), SwingConstants.CENTER));




        setVisible(true);
    }








    private double carregarFundos() {



   double total = 0.0;


    Path caminho = Paths.get("usuarios", emailUsuario, "fundos.txt");


   if (Files.exists(caminho)) {

 try {
 List<String> linhas = Files.readAllLines(caminho);


         for (String linha : linhas) {


                    total += Double.parseDouble(linha.trim().replace(",", "."));



     }


     } catch (IOException | NumberFormatException e) {



                System.out.println("Erro ao ler fundos: " + e.getMessage());



    }
        }



        return total;



    }




    private double carregarGastos() {
  double total = 0.0;

  Path caminho = Paths.get("usuarios", emailUsuario, "historico_financas.txt");


    if (Files.exists(caminho)) {


        try {
                List<String> linhas = Files.readAllLines(caminho);

      for (String linha : linhas) {


         String[] partes = linha.split(";");


      if (partes.length >= 1) {



                        total += Double.parseDouble(partes[0].trim().replace(",", "."));


     }
                }



            } catch (IOException | NumberFormatException e) {


   System.out.println("Erro ao ler gastos: " + e.getMessage());




            }
        }




        return total;
    }
}
