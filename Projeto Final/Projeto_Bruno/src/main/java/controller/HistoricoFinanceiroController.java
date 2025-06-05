package controller;
import dao.TransacaoDAO;
import dao.TransacaoDAOImpl;
import model.Transacao;
import util.Sessao;
import view.InterfaceView;
import view.TelaVerHistoricoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoFinanceiroController {

    private final TelaVerHistoricoView view;
    private final TransacaoDAO transacaoDAO;



    public HistoricoFinanceiroController(InterfaceView interfaceView) {
        this.view = new TelaVerHistoricoView();
        this.transacaoDAO = new TransacaoDAOImpl();

        carregarCategorias();
        carregarTabela();

        view.adicionarListenerFiltrar(new FiltrarListener());

        view.setVisible(true);
    }



    private void carregarCategorias() {
        List<Transacao> todasTransacoes = transacaoDAO.buscarTodas(Sessao.getIdUsuario());
        List<String> categorias = todasTransacoes.stream()
                .map(Transacao::getCategoria)
                .distinct()
                .collect(Collectors.toList());
        view.setCategorias(categorias);
    }


    private void carregarTabela() {
        List<Transacao> transacoes = transacaoDAO.buscarTodas(Sessao.getIdUsuario());
        view.atualizarTabela(transacoes);
    }



    private void filtrarTabela() {
        String tipo = view.getTipoFiltro();
        String categoria = view.getCategoriaFiltro();
        String dataInicio = view.getDataInicioFiltro();
        String dataFim = view.getDataFimFiltro();

        try {
            List<Transacao> transacoesFiltradas = transacaoDAO.filtrarTransacoes(Sessao.getIdUsuario(), dataInicio, dataFim, tipo, categoria);
            view.atualizarTabela(transacoesFiltradas);
        } catch (Exception e) {
            view.mostrarMensagemErro("Erro ao filtrar transações: " + e.getMessage());
        }
    }



    private class FiltrarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            filtrarTabela();
        }
    }
}
