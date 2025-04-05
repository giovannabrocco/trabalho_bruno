import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;




public class TelaVerHistorico extends JFrame {




private DefaultTableModel tabelaModel;
 private String emailUsuario;





public TelaVerHistorico(String emailUsuario) {
        this.emailUsuario = emailUsuario;



   setTitle("Histórico de Finanças");
    setSize(600, 400);





  setLocationRelativeTo(null);
        setLayout(new BorderLayout());



  String[] colunas = {"Valor (R$)", "Categoria", "Descrição", "Data"};


    tabelaModel = new DefaultTableModel(colunas, 0);

        JTable tabela = new JTable(tabelaModel);

        JScrollPane scrollPane = new JScrollPane(tabela);




 scrollPane.setBorder(new TitledBorder("Últimas Ações"));
  add(scrollPane, BorderLayout.CENTER);





        carregarHistorico();
        setVisible(true);


    }





    private void carregarHistorico() {



   try {
         Path caminho = Paths.get("usuarios", emailUsuario, "historico_financas.txt");


         if (Files.exists(caminho)) {


              List<String> linhas = Files.readAllLines(caminho);

                tabelaModel.setRowCount(0);

        for (String linha : linhas) {




                    String[] partes = linha.split(";");
                    if (partes.length == 4) {
                        tabelaModel.addRow(partes);




     }
      }



       } else {


            JOptionPane.showMessageDialog(this, "Nenhum histórico encontrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);


      }




    } catch (IOException e) {


    JOptionPane.showMessageDialog(this, "Erro ao carregar histórico.", "Erro", JOptionPane.ERROR_MESSAGE);









   }
    }
}
