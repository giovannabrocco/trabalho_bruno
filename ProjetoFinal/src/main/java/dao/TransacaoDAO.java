
package dao;

import model.Transacao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class TransacaoDAO implements GenericDAO<Transacao> {

    public void salvar(Transacao transacao) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(transacao);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void atualizar(Transacao transacao) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(transacao);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(Transacao transacao) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(transacao);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Transacao buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Transacao.class, id);
        }
    }

    public List<Transacao> buscarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Transacao", Transacao.class).list();
        }
    }


    public List<Transacao> buscarTodos(model.Usuario usuario) {
        try (Session session = util.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Transacao where usuario = :usuario", Transacao.class)
                    .setParameter("usuario", usuario)
                    .list();
        }
    }
}