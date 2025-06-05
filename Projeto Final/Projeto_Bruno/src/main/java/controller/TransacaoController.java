
package controller;
import dao.TransacaoDAO;
import dao.TransacaoDAOImpl;
import model.Transacao;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Sessao;
import java.util.List;

public class TransacaoController {

    private final TransacaoDAO transacaoDAO;


    public TransacaoController() {
        this.transacaoDAO = new TransacaoDAOImpl();
    }

    public List<Transacao> buscarUltimasTransacoes(int limite) {
        return transacaoDAO.buscarUltimasTransacoes(util.Sessao.getIdUsuario(), limite);
    }


    public double calcularSaldo() {
        double receitas = 0.0;
        double despesas = 0.0;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {


            String hqlReceitas = "SELECT SUM(t.valor) FROM Transacao t WHERE t.idUsuario = :idUsuario AND t.tipo = 'receita'";
            String hqlDespesas = "SELECT SUM(t.valor) FROM Transacao t WHERE t.idUsuario = :idUsuario AND t.tipo = 'despesa'";

            Double r = session.createQuery(hqlReceitas, Double.class)
                    .setParameter("idUsuario", Sessao.getIdUsuario())
                    .uniqueResultOptional().orElse(0.0);
            Double d = session.createQuery(hqlDespesas, Double.class)
                    .setParameter("idUsuario", Sessao.getIdUsuario())
                    .uniqueResultOptional().orElse(0.0);


            receitas = r;
            despesas = d;



        } catch (Exception e) {
            System.out.println("Erro ao calcular saldo: " + e.getMessage());
            e.printStackTrace();
        }



        return receitas - despesas;
    }
}

