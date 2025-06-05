package dao;
import model.Transacao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ResumoFinanceiroDAOImpl implements ResumoFinanceiroDAO {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public double calcularTotalReceitas(int idUsuario, String dataInicioStr, String dataFimStr) {

        return calcularTotalPorTipo(idUsuario, "receita", dataInicioStr, dataFimStr);
    }

    @Override
    public double calcularTotalDespesas(int idUsuario, String dataInicioStr, String dataFimStr) {
        return calcularTotalPorTipo(idUsuario, "despesa", dataInicioStr, dataFimStr);
    }

    private double calcularTotalPorTipo(int idUsuario, String tipo, String dataInicioStr, String dataFimStr) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        double total = 0.0;

        try {

            LocalDate dataInicio = null;
            LocalDate dataFim = null;


            try {
                if (dataInicioStr != null && !dataInicioStr.isEmpty() && !dataInicioStr.contains("_")) {
                    dataInicio = LocalDate.parse(dataInicioStr, DATE_FORMATTER);
                }
                if (dataFimStr != null && !dataFimStr.isEmpty() && !dataFimStr.contains("_")) {
                    dataFim = LocalDate.parse(dataFimStr, DATE_FORMATTER);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Erro ao parsear datas de filtro: " + e.getMessage());
                return 0.0;
            }


            String hql = "FROM Transacao t WHERE t.idUsuario = :idUsuario AND t.tipo = :tipo";
            Query<Transacao> query = session.createQuery(hql, Transacao.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("tipo", tipo);



            List<Transacao> transacoes = query.list();


            for (Transacao t : transacoes) {
                try {
               LocalDate dataTransacao = LocalDate.parse(t.getData(), DATE_FORMATTER);


               boolean dentroDoIntervalo = true;
                  if (dataInicio != null && dataTransacao.isBefore(dataInicio)) {
                        dentroDoIntervalo = false;
                    }
                    if (dataFim != null && dataTransacao.isAfter(dataFim)) {
                        dentroDoIntervalo = false;
                    }

                    if (dentroDoIntervalo) {
                        total += t.getValor();
                    }
                } catch (DateTimeParseException e) {
                    System.err.println("Erro ao parsear data da transação ID " + t.getId() + ": " + t.getData() + " - " + e.getMessage());
                }
            }



        } catch (Exception e) {
            System.err.println("Erro ao calcular total por tipo (" + tipo + "): " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }

        return total;
    }
}

