package dao;

import model.Transacao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Sessao;

import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    public void adicionarTransacao(Transacao transacao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            transacao.setIdUsuario(Sessao.getIdUsuario());
            session.save(transacao);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro ao adicionar transação: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Transacao> carregarTransacoes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Transacao WHERE idUsuario = :idUsuario ORDER BY data DESC";
            Query<Transacao> query = session.createQuery(hql, Transacao.class);
            query.setParameter("idUsuario", Sessao.getIdUsuario());
            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao carregar transações: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }

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
            if (categoria != null && !categoria.equalsIgnoreCase("Todas")) query.setParameter("categoria", categoria);

            return query.list();
        } catch (Exception e) {
            System.out.println("Erro ao filtrar transações: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }
}
