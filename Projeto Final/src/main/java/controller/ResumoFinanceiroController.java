package controller;

import dao.ResumoFinanceiroDAO;

public class ResumoFinanceiroController {
    private ResumoFinanceiroDAO resumoFinanceiroDAO;

    public ResumoFinanceiroController() {
        this.resumoFinanceiroDAO = new ResumoFinanceiroDAO();
    }

    public double calcularTotalReceitas(String dataInicio, String dataFim) {
        return resumoFinanceiroDAO.consultarTotal("receita", dataInicio, dataFim);
    }

    public double calcularTotalDespesas(String dataInicio, String dataFim) {
        return resumoFinanceiroDAO.consultarTotal("despesa", dataInicio, dataFim);
    }

    public double calcularSaldo(String dataInicio, String dataFim) {
        double receitas = calcularTotalReceitas(dataInicio, dataFim);
        double despesas = calcularTotalDespesas(dataInicio, dataFim);
        return receitas - despesas;
    }
}
