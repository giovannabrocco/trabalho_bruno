
package dao;

import model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class UsuarioDAO implements GenericDAO<Usuario> {

    public void salvar(Usuario usuario) {
        Transaction transacao = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transacao = session.beginTransaction();
            session.save(usuario);
            transacao.commit();
        } catch (Exception e) {
            if (transacao != null) transacao.rollback();
            e.printStackTrace();
        }
    }

    public void atualizar(Usuario usuario) {
        Transaction transacao = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transacao = session.beginTransaction();
            session.update(usuario);
            transacao.commit();
        } catch (Exception e) {
            if (transacao != null) transacao.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(Usuario usuario) {
        Transaction transacao = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transacao = session.beginTransaction();
            session.delete(usuario);
            transacao.commit();
        } catch (Exception e) {
            if (transacao != null) transacao.rollback();
            e.printStackTrace();
        }
    }

    public Usuario buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Usuario.class, id);
        }
    }

    public List<Usuario> buscarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Usuario", Usuario.class).list();
        }
    }

    public Usuario buscarPorEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Usuario where email = :email", Usuario.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }
}
