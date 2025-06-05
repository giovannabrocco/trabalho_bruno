package dao;
import model.Transacao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Sessao;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAOImpl implements TransacaoDAO {



    @Override
    public void adicionarTransacao(Transacao transacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            transacao.setIdUsuario(Sessao.getIdUsuario());
            session.save(transacao);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Erro ao adicionar transação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



    @Override
    public List<Transacao> carregarTransacoes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Transacao WHERE idUsuario = :idUsuario ORDER BY data DESC";
            Query<Transacao> query = session.createQuery(hql, Transacao.class);
            query.setParameter("idUsuario", Sessao.getIdUsuario());
            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao carregar transações: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }




    @Override
    public List<Transacao> filtrarTransacoes(String data, String tipo, String categoria) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            StringBuilder hql = new StringBuilder("FROM Transacao WHERE idUsuario = :idUsuario");

            if (data != null && !data.isEmpty()) hql.append(" AND data = :data");
            if (tipo != null && !tipo.equalsIgnoreCase("Todos")) hql.append(" AND tipo = :tipo");
            if (categoria != null && !categoria.equalsIgnoreCase("Todas")) hql.append(" AND categoria = :categoria");
            hql.append(" ORDER BY data DESC");

            Query<Transacao> query = session.createQuery(hql.toString(), Transacao.class);
            query.setParameter("idUsuario", Sessao.getIdUsuario());

            if (data != null && !data.isEmpty()) query.setParameter("data", data);
            if (tipo != null && !tipo.equalsIgnoreCase("Todos")) query.setParameter("tipo", tipo.toLowerCase());
            if (categoria != null && !categoria.equalsIgnoreCase("Todos")) query.setParameter("categoria", categoria);

            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao filtrar transações (3 strings): " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }



    @Override
    public boolean salvar(Transacao transacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean sucesso = false;
        try {
            tx = session.beginTransaction();
            transacao.setIdUsuario(Sessao.getIdUsuario()); // Garante que o ID do usuário da sessão seja usado
            session.save(transacao);
            tx.commit();
            sucesso = true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Erro ao salvar transação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return sucesso;
    }




    @Override
    public List<Transacao> buscarTodas(int idUsuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Transacao WHERE idUsuario = :idUsuario ORDER BY data DESC";
            Query<Transacao> query = session.createQuery(hql, Transacao.class);
            query.setParameter("idUsuario", idUsuario);
            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao buscar todas as transações: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }



    @Override
    public List<Transacao> buscarUltimasTransacoes(int idUsuario, int limite) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Transacao WHERE idUsuario = :idUsuario ORDER BY id DESC";
            Query<Transacao> query = session.createQuery(hql, Transacao.class);
            query.setParameter("idUsuario", idUsuario);
            query.setMaxResults(limite);
            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao buscar últimas transações: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Transacao> filtrarTransacoes(int idUsuario, String dataInicio, String dataFim, String tipo, String categoria) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

            StringBuilder hql = new StringBuilder("FROM Transacao WHERE idUsuario = :idUsuario");

            if (dataInicio != null && !dataInicio.isEmpty()) hql.append(" AND data >= :dataInicio");
            if (dataFim != null && !dataFim.isEmpty()) hql.append(" AND data <= :dataFim");
            if (tipo != null && !tipo.equalsIgnoreCase("Todos") && !tipo.isEmpty()) hql.append(" AND tipo = :tipo");
            if (categoria != null && !categoria.equalsIgnoreCase("Todas") && !categoria.isEmpty()) hql.append(" AND categoria = :categoria");
            hql.append(" ORDER BY data DESC");

            Query<Transacao> query = session.createQuery(hql.toString(), Transacao.class);
            query.setParameter("idUsuario", idUsuario);

            if (dataInicio != null && !dataInicio.isEmpty()) query.setParameter("dataInicio", dataInicio);
            if (dataFim != null && !dataFim.isEmpty()) query.setParameter("dataFim", dataFim);
            if (tipo != null && !tipo.equalsIgnoreCase("Todos") && !tipo.isEmpty()) query.setParameter("tipo", tipo.toLowerCase());
            if (categoria != null && !categoria.equalsIgnoreCase("Todas") && !categoria.isEmpty()) query.setParameter("categoria", categoria);

            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao filtrar transações (com datas): " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }
}

