import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaGerenciarFinancas extends JFrame {
    private JFormattedTextField campoValor;
    private JComboBox<String> campoCategoria;
    private JTextField campoDescricao;
    private JFormattedTextField campoData;
    private TelaPrincipal telaPrincipal;
    private DecimalFormat formatoMoeda = new DecimalFormat("#,##0.00");

    public TelaGerenciarFinancas(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        setTitle("Gerenciar Finanças");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Valor (R$):"));
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        formatoNumero.setMinimumFractionDigits(2);
        formatoNumero.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(formatoNumero);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.0);
        campoValor = new JFormattedTextField(new DefaultFormatterFactory(formatter));
        campoValor.setColumns(10);
        add(campoValor);

        add(new JLabel("Categoria:"));
        campoCategoria = new JComboBox<>(new String[]{"Alimentação", "Transporte", "Lazer", "Saúde", "Outros"});
        add(campoCategoria);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextField();
        add(campoDescricao);

        add(new JLabel("Data (dd/MM/yyyy):"));
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            campoData = new JFormattedTextField(new DefaultFormatterFactory(mascaraData));
        } catch (ParseException e) {
            campoData = new JFormattedTextField();
        }
        add(campoData);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarRegistro();
            }
        });
        add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        setVisible(true);
    }

    private void salvarRegistro() {
        try {
            double valor = formatoMoeda.parse(campoValor.getText()).doubleValue();
            String categoria = (String) campoCategoria.getSelectedItem();
            String descricao = campoDescricao.getText();
            String data = campoData.getText();

            // Registrar o gasto na tela principal
            telaPrincipal.registrarGasto(valor, categoria, descricao, data);

            // Salvar no arquivo
            salvarNoArquivo(valor, categoria, descricao, data);

            JOptionPane.showMessageDialog(this, "Registro salvo com sucesso!");
            dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarNoArquivo(double valor, String categoria, String descricao, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_financas.txt", true))) {
            writer.write(valor + ";" + categoria + ";" + descricao + ";" + data);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
