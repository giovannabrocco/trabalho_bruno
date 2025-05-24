package dao;

import model.Transacao
        ;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Sessao;

public class ResumoFinanceiroDAO {

    public double consultarTotal(String tipo, String dataInicio, String dataFim) {
        double total = 0.0;

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            StringBuilder hql = new StringBuilder("SELECT SUM(t.valor) FROM Transacao t WHERE t.idUsuario = :idUsuario AND t.tipo = :tipo");

            if (dataInicio != null && !dataInicio.isEmpty()) {
                hql.append(" AND t.data >= :dataInicio");
            }
            if (dataFim != null && !dataFim.isEmpty()) {
                hql.append(" AND t.data <= :dataFim");
            }

            Query<Double> query = session.createQuery(hql.toString(), Double.class);
            query.setParameter("idUsuario", Sessao.getIdUsuario());
            query.setParameter("tipo", tipo);

            if (dataInicio != null && !dataInicio.isEmpty()) {
                query.setParameter("dataInicio", dataInicio);
            }
            if (dataFim != null && !dataFim.isEmpty()) {
                query.setParameter("dataFim", dataFim);
            }

            Double resultado = query.uniqueResult();
            if (resultado != null) {
                total = resultado;
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar total de " + tipo + ": " + e.getMessage());
        } finally {
            session.close();
        }

        return total;
    }
}
