package controller;
import dao.ResumoFinanceiroDAO;
import dao.ResumoFinanceiroDAOImpl;
import util.Sessao;
import view.TelaResumoFinanceiroView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class ResumoFinanceiroController {

    private final ResumoFinanceiroDAO dao;

    private final TelaResumoFinanceiroView view;
    private final NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));


    public ResumoFinanceiroController(TelaResumoFinanceiroView view) {
        this.view = view;
        this.dao = new ResumoFinanceiroDAOImpl();


        this.view.setFiltrarListener(new FiltrarListener());


        atualizarResumoGeral();
    }


    private void filtrarResumoPorData() {
        String dataInicio = view.getDataInicio();
        String dataFim = view.getDataFim();

        if (!isValidDate(dataInicio) || !isValidDate(dataFim)) {
            view.exibirMensagemErro("Por favor, insira datas válidas no formato dd/MM/yyyy.");
            return;
        }

        try {
            double totalReceitas = dao.calcularTotalReceitas(Sessao.getIdUsuario(), dataInicio, dataFim);
            double totalDespesas = dao.calcularTotalDespesas(Sessao.getIdUsuario(), dataInicio, dataFim);
            double saldo = totalReceitas - totalDespesas;


            view.atualizarResumo(totalReceitas, totalDespesas, saldo);

        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao filtrar resumo financeiro: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void atualizarResumoGeral() {
        try {

            double totalReceitas = dao.calcularTotalReceitas(Sessao.getIdUsuario(), null, null);
            double totalDespesas = dao.calcularTotalDespesas(Sessao.getIdUsuario(), null, null);
            double saldo = totalReceitas - totalDespesas;

            view.atualizarResumo(totalReceitas, totalDespesas, saldo);


        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao carregar resumo financeiro geral: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean isValidDate(String date) {
        return date != null && date.matches("\\d{2}/\\d{2}/\\d{4}") && !date.contains("_");
    }


    private class FiltrarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            filtrarResumoPorData();
        }
    }


}

