
package dao;

import model.CategoriaFinanceira;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class CategoriaFinanceiraDAO implements GenericDAO<CategoriaFinanceira> {

    public void salvar(CategoriaFinanceira categoria) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(categoria);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void atualizar(CategoriaFinanceira categoria) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(categoria);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(CategoriaFinanceira categoria) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(categoria);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public CategoriaFinanceira buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(CategoriaFinanceira.class, id);
        }
    }

    public List<CategoriaFinanceira> buscarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CategoriaFinanceira", CategoriaFinanceira.class).list();
        }
    }


    public List<CategoriaFinanceira> buscarTodos(model.Usuario usuario) {
        try (Session session = util.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CategoriaFinanceira where usuario = :usuario", CategoriaFinanceira.class)
                    .setParameter("usuario", usuario)
                    .list();
        }
    }
}
