package controller;

import dao.TransacaoDAO;
import model.Transacao;

import java.util.List;

public class HistoricoFinanceiroController {
    private TransacaoDAO transacaoDAO;

    public HistoricoFinanceiroController() {
        this.transacaoDAO = new TransacaoDAO();
    }

    public List<Transacao> filtrarTransacoes(String data, String tipo, String categoria) {
        return transacaoDAO.filtrarTransacoes(data, tipo, categoria);
    }
}
