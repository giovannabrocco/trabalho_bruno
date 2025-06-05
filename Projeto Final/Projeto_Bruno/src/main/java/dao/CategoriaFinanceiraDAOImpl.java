package dao;
import model.CategoriaFinanceira;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Sessao;
import java.util.List;

public class CategoriaFinanceiraDAOImpl implements CategoriaFinanceiraDAO {

    @Override
    public List<CategoriaFinanceira> listar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

            String hql = "FROM CategoriaFinanceira WHERE idUsuario = :idUsuario";
            Query<CategoriaFinanceira> query = session.createQuery(hql, CategoriaFinanceira.class);
            query.setParameter("idUsuario", Sessao.getIdUsuario());
            return query.list();

          } finally {
            session.close();
        }
    }



    @Override
    public void adicionar(CategoriaFinanceira categoria) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(categoria);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }




    @Override
    public void editar(int id, String novoNome) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            CategoriaFinanceira categoria = session.get(CategoriaFinanceira.class, id);
            if (categoria != null && categoria.getIdUsuario() == Sessao.getIdUsuario()) {
                categoria.setNome(novoNome);
                session.update(categoria);
                tx.commit();
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public void salvar(CategoriaFinanceira categoria) {
        adicionar(categoria);
    }

    @Override
    public void excluir(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            CategoriaFinanceira categoria = session.get(CategoriaFinanceira.class, id);
            if (categoria != null && categoria.getIdUsuario() == Sessao.getIdUsuario()) {
                session.delete(categoria);
                tx.commit();
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
