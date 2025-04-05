import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        // Iniciar a interface gráfica
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interface();  // Inicia a interface gráfica
            }
        });
    }
}
