import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import model.Transacao;
import model.Usuario;

public class TelaResumoFinanceiro extends JFrame {

    private JTextField campoDataInicio;
    private JTextField campoDataFim;
    private JLabel labelReceitas;
    private JLabel labelDespesas;
    private JLabel labelSaldo;
    private Usuario usuario;
    private Financeiro financeiro;

    public TelaResumoFinanceiro(Usuario usuario) {
        this.usuario = usuario;
        this.financeiro = new Financeiro(usuario);

        setTitle("Resumo Financeiro");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Data Início (AAAA-MM-DD):"));
        campoDataInicio = new JTextField();
        add(campoDataInicio);

        add(new JLabel("Data Fim (AAAA-MM-DD):"));
        campoDataFim = new JTextField();
        add(campoDataFim);

        JButton btnGerar = new JButton("Gerar Resumo");
        add(btnGerar);
        add(new JLabel());  // espaço vazio

        labelReceitas = new JLabel("Receitas: R$ 0.00");
        labelDespesas = new JLabel("Despesas: R$ 0.00");
        labelSaldo = new JLabel("Saldo: R$ 0.00");

        add(labelReceitas);
        add(labelDespesas);
        add(labelSaldo);

        btnGerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalDate inicio = LocalDate.parse(campoDataInicio.getText());
                    LocalDate fim = LocalDate.parse(campoDataFim.getText());

                    double receitas = 0.0;
                    double despesas = 0.0;

                    List<Transacao> transacoes = financeiro.getTransacoes();

                    for (Transacao t : transacoes) {
                        LocalDate dataTransacao = LocalDate.parse(t.getDataTexto());

                        if (!dataTransacao.isBefore(inicio) && !dataTransacao.isAfter(fim)) {
                            if ("Receita".equalsIgnoreCase(t.getTipo())) {
                                receitas += t.getValor();
                            } else if ("Despesa".equalsIgnoreCase(t.getTipo())) {
                                despesas += t.getValor();
                            }
                        }
                    }

                    double saldo = receitas - despesas;
                    labelReceitas.setText("Receitas: R$ " + String.format("%.2f", receitas));
                    labelDespesas.setText("Despesas: R$ " + String.format("%.2f", despesas));
                    labelSaldo.setText("Saldo: R$ " + String.format("%.2f", saldo));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Datas inválidas. Use o formato AAAA-MM-DD.");
                }
            }
        });
    }
}
