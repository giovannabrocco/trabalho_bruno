package util;

import model.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration().configure(); // Lê hibernate.cfg.xml
            cfg.addAnnotatedClass(model.Usuario.class);
            // Registra a entidade anotada

            return cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("❌ Erro ao criar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
