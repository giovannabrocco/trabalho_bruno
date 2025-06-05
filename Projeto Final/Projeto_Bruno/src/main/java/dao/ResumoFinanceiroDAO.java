package dao;

public interface ResumoFinanceiroDAO {
    double calcularTotalReceitas(int idUsuario, String dataInicio, String dataFim);
    double calcularTotalDespesas(int idUsuario, String dataInicio, String dataFim);
}
