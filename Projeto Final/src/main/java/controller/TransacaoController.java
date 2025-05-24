package controller;

import dao.TransacaoDAO;
import model.Transacao;
import util.ConexaoSQLite;
import util.Sessao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import util.HibernateUtil;


public class TransacaoController {
    private TransacaoDAO transacaoDAO;

    public TransacaoController() {
        this.transacaoDAO = new TransacaoDAO();
    }

    public boolean adicionarTransacao(Transacao transacao) {
        if (transacao.getDescricao() == null || transacao.getDescricao().isEmpty()) return false;
        if (transacao.getValor() <= 0) return false;
        if (!transacao.getData().matches("\\d{2}/\\d{2}/\\d{4}")) return false;

        try {
            transacaoDAO.adicionarTransacao(transacao);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao adicionar transação: " + e.getMessage());
            return false;
        }
    }

    public List<Transacao> listarTransacoes() {
        return transacaoDAO.carregarTransacoes();
    }

    public List<Transacao> listarUltimasTransacoes(int limite) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Transacao WHERE idUsuario = :idUsuario ORDER BY id DESC";
            return session.createQuery(hql, Transacao.class)
                    .setParameter("idUsuario", Sessao.getIdUsuario())
                    .setMaxResults(limite)
                    .list();
        } catch (Exception e) {
            System.out.println("Erro ao listar últimas transações: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public double calcularSaldo() {
        double receitas = 0.0;
        double despesas = 0.0;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hqlReceitas = "SELECT SUM(valor) FROM Transacao WHERE idUsuario = :idUsuario AND tipo = 'receita'";
            String hqlDespesas = "SELECT SUM(valor) FROM Transacao WHERE idUsuario = :idUsuario AND tipo = 'despesa'";

            Double r = session.createQuery(hqlReceitas, Double.class)
                    .setParameter("idUsuario", Sessao.getIdUsuario())
                    .uniqueResult();
            Double d = session.createQuery(hqlDespesas, Double.class)
                    .setParameter("idUsuario", Sessao.getIdUsuario())
                    .uniqueResult();

            receitas = r != null ? r : 0.0;
            despesas = d != null ? d : 0.0;

        } catch (Exception e) {
            System.out.println("Erro ao calcular saldo: " + e.getMessage());
        }

        return receitas - despesas;
    }


    public void registrarGasto(Transacao gasto) {
        gasto = new Transacao("despesa", gasto.getCategoria(), gasto.getDescricao(), gasto.getValor(), gasto.getData());
        adicionarTransacao(gasto);
    }
}
