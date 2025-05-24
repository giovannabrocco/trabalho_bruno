package view;

import util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        // Inicializa o Hibernate
        HibernateUtil.getSessionFactory();

        // Garante fechamento da SessionFactory ao sair
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            HibernateUtil.shutdown();
        }));

        // Abre a primeira tela
        new TelaInicial();
    }
}
