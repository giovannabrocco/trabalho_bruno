package view;
import controller.TransacaoController;
import model.Transacao;
import util.Sessao;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InterfaceView extends JFrame {
    private JLabel displayValor;
    private JTable tabela;
    private DefaultTableModel tabelaModel;

    private JButton btnVerHistorico;
    private JButton btnGerenciar;
    private JButton btnResumoFinanceiro;
    private JButton btnSair;

    private final TransacaoController transacaoController;
    private static final int LIMITE_ULTIMOS_REGISTROS = 10;
    private final NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));


    public InterfaceView(TransacaoController transacaoController) {
        this.transacaoController = transacaoController;

        String emailUsuario = Sessao.getEmailUsuario();




        setTitle("Bem Vindo ao Sistema!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));

        // --- Configuração da Tabela ---
        String[] colunas = {"Valor (R$)", "Tipo", "Categoria", "Descrição", "Data"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Células não editáveis
            }
        };

        tabela = new JTable(tabelaModel);
        tabela.setRowHeight(30);
        tabela.setFillsViewportHeight(true);
        tabela.setFont(new Font("Century Gothic", Font.PLAIN, 14));


        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(700, 200));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Movimentações recentes",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Century Gothic", Font.BOLD, 14)
        ));



        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(Color.WHITE);
        painelCentral.add(scrollPane);


        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBackground(Color.WHITE);



        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        painelBotoes.setBackground(Color.WHITE);

        btnVerHistorico = criarBotao("Histórico Financeiro");
        btnGerenciar = criarBotao("Gerenciador Financeiro");
        btnResumoFinanceiro = criarBotao("Resumo Financeiro");



        painelBotoes.add(btnVerHistorico);
        painelBotoes.add(btnGerenciar);
        painelBotoes.add(btnResumoFinanceiro);



        JPanel painelDisplay = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDisplay.setBackground(Color.WHITE);
        displayValor = new JLabel("R$ 0,00"); // Valor inicial
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




        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.WHITE);

        btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnSair.setBackground(Color.BLACK);
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setPreferredSize(new Dimension(80, 30));

        JPanel painelDireitaTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDireitaTopo.setBackground(Color.WHITE);
        painelDireitaTopo.add(btnSair);

        JLabel labelUsuario = new JLabel("Usuário: " + (emailUsuario != null ? emailUsuario : "N/A"));
        labelUsuario.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        labelUsuario.setForeground(Color.DARK_GRAY);
        labelUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelTopo.add(labelUsuario, BorderLayout.WEST);
        painelTopo.add(painelDireitaTopo, BorderLayout.EAST);


        add(painelTopo, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);


        recarregarSaldoEDisplay();
        atualizarTabelaUltimosRegistros();
    }


    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Century Gothic", Font.BOLD, 14));
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);

        return botao;
    }




    public void adicionarBtnVerHistoricoListener(ActionListener l) {
        btnVerHistorico.addActionListener(l);
    }

    public void adicionarBtnGerenciarListener(ActionListener l) {
        btnGerenciar.addActionListener(l);
    }

    public void adicionarBtnResumoFinanceiroListener(ActionListener l) {
        btnResumoFinanceiro.addActionListener(l);
    }

    public void adicionarBtnSairListener(ActionListener l) {
        btnSair.addActionListener(l);
    }


    public void recarregarSaldoEDisplay() {
        try {
            double saldo = transacaoController.calcularSaldo();
            displayValor.setText(formatadorMoeda.format(saldo));


            if (saldo < 0) {
              displayValor.setForeground(Color.RED);
            } else {
             displayValor.setForeground(Color.BLACK);
            }
        } catch (Exception e) {
            System.err.println("Erro ao recarregar saldo: " + e.getMessage());
            e.printStackTrace();
            displayValor.setText("Erro");
            displayValor.setForeground(Color.RED);
        }
    }



    public void atualizarTabelaUltimosRegistros() {
        try {
            List<Transacao> transacoes = transacaoController.buscarUltimasTransacoes(LIMITE_ULTIMOS_REGISTROS);




            tabelaModel.setRowCount(0);


            for (Transacao t : transacoes) {
                Object[] rowData = {
                   formatadorMoeda.format(t.getValor()),
                     t.getTipo(),
                      t.getCategoria(),
                        t.getDescricao(),
                       t.getData()
                };
                tabelaModel.addRow(rowData);
            }

        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela de últimos registros: " + e.getMessage());
            e.printStackTrace();

        }
    }
}

