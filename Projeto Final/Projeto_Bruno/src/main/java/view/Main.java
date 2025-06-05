package view;

import util.HibernateUtil;
import controller.UsuarioController;


public class Main {
    public static void main(String[] args) {
  
        HibernateUtil.getSessionFactory();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            HibernateUtil.shutdown();
        }));


        new UsuarioController();

    }
}
