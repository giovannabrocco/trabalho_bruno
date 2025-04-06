import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CategoriaFinanceira.carregarArquivo();
            new CategoriaFinanceiraView().setVisible(true);
        });
    }
}
