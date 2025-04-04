import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



public class TelaPrincipal extends JFrame {
    private JLabel displayValor;
    private double valorDisponivel = 0.0;
    private DefaultTableModel tabelaModel;



    //aqui eu inicio a tela principal


  public TelaPrincipal() {
     setTitle("Bem Vindo ao Sistema");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setSize(900, 650);
    setLocationRelativeTo(null);
     getContentPane().setBackground(Color.WHITE);
    setLayout(new BorderLayout(10, 10));




  String[] colunas = {"Valor (R$)", "Categoria", "Descrição", "Data"};
  tabelaModel = new DefaultTableModel(colunas, 0);
   JTable tabela = new JTable(tabelaModel);
   tabela.setRowHeight(30);
   tabela.setFillsViewportHeight(true);
 tabela.setFont(new Font("Arial", Font.PLAIN, 14));




  JScrollPane scrollPane = new JScrollPane(tabela);
   scrollPane.setPreferredSize(new Dimension(700, 200));
   scrollPane.setBorder(BorderFactory.createTitledBorder(
          BorderFactory.createLineBorder(Color.GRAY, 1),
         "Movimentações recentes",
           TitledBorder.LEFT,
            TitledBorder.TOP,
               new Font("Arial", Font.BOLD, 14)
        ));





 JPanel painelCentral = new JPanel(new GridBagLayout());

        painelCentral.setBackground(Color.WHITE);

     painelCentral.add(scrollPane);




 JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBackground(Color.WHITE);



   JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        painelBotoes.setBackground(Color.WHITE);



        JButton btnVerHistorico = criarBotao("Ver Histórico");

        btnVerHistorico.addActionListener(e -> new TelaVerHistorico());



    JButton btnGerenciar = criarBotao("Gerenciar Finanças");
        btnGerenciar.addActionListener(e -> new TelaGerenciarFinancas(this));



   JButton btnRegistrarFundo = criarBotao("Registrar Fundo");

        btnRegistrarFundo.addActionListener(e -> new TelaRegistrarFundo(this));


      JButton btnResumoFinanceiro = criarBotao("Resumo Financeiro");
btnResumoFinanceiro.addActionListener(e -> new TelaResumoFinanceiro());





//painel de botoes
    painelBotoes.add(btnVerHistorico);
    painelBotoes.add(btnGerenciar);
    painelBotoes.add(btnRegistrarFundo);
   painelBotoes.add(btnResumoFinanceiro);





        //Aqui eu coloco a parte do display .
    JPanel painelDisplay = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    painelDisplay.setBackground(Color.WHITE);
   displayValor = new JLabel("R$ 0,00");
    displayValor.setFont(new Font("Arial", Font.BOLD, 20));
     displayValor.setForeground(Color.BLACK);



 JPanel caixaDisplay = new JPanel();
    caixaDisplay.setBorder(BorderFactory.createLineBorder(Color.GRAY));
   caixaDisplay.setPreferredSize(new Dimension(180, 45));
    caixaDisplay.setBackground(Color.WHITE);
   caixaDisplay.add(displayValor);




  painelDisplay.add(caixaDisplay);

 painelInferior.add(painelBotoes, BorderLayout.WEST);
 painelInferior.add(painelDisplay, BorderLayout.EAST);




        //aqui eu vou tentar organizar os componentes
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);




        // preciso puxar os ultimos registros da tabela

        atualizarTabelaUltimosRegistros();

        setVisible(true);
    }



    //Os botoes


    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        return botao;
    }



//esse display ta me irritando, atualizar aquiii


 public void atualizarValorDisplay(double valor) {
        this.valorDisponivel += valor;
        atualizarCorDisplay();
    }





 public void registrarGasto(double valor, String categoria, String descricao, String data) {

        tabelaModel.insertRow(0, new Object[]{

      String.format("R$ %.2f", valor), categoria, descricao, data

        });


   this.valorDisponivel -= valor;

    atualizarCorDisplay();



    if (tabelaModel.getRowCount() > 5) {

            tabelaModel.removeRow(5);


        }
    }






  private void atualizarCorDisplay() {

        if (valorDisponivel < 0) {

            displayValor.setForeground(Color.RED);

        } else {

            displayValor.setForeground(Color.BLACK);

        }

        displayValor.setText(String.format("R$ %.2f", valorDisponivel));

    }




    public void atualizarTabelaUltimosRegistros() {

        try {

            List<String> linhas = Files.readAllLines(Paths.get("historico_financas.txt"));
            tabelaModel.setRowCount(0);
            int inicio = Math.max(0, linhas.size() - 5);
            for (int i = linhas.size() - 1; i >= inicio; i--) {
                String[] dados = linhas.get(i).split(";");
                if (dados.length == 4) {
                    tabelaModel.addRow(dados);

                }
            }

        }
        catch (IOException e) {

            System.out.println("Erro ao ler o histórico: " + e.getMessage());

        }
    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(TelaPrincipal::new);

    }
}
