
package dao;

import model.HistoricoFinanceiro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class HistoricoFinanceiroDAO implements GenericDAO<HistoricoFinanceiro> {

    public void salvar(HistoricoFinanceiro historico) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(historico);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void atualizar(HistoricoFinanceiro historico) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(historico);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(HistoricoFinanceiro historico) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(historico);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public HistoricoFinanceiro buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HistoricoFinanceiro.class, id);
        }
    }

    public List<HistoricoFinanceiro> buscarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from HistoricoFinanceiro", HistoricoFinanceiro.class).list();
        }
    }


    public List<HistoricoFinanceiro> buscarTodos(model.Usuario usuario) {
        try (Session session = util.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from HistoricoFinanceiro where usuario = :usuario", HistoricoFinanceiro.class)
                    .setParameter("usuario", usuario)
                    .list();
        }
    }
}