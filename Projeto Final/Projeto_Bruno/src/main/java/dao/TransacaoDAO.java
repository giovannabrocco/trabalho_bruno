package dao;
import model.Transacao;
import java.util.List;

public interface TransacaoDAO {

    void adicionarTransacao(Transacao transacao);

    List<Transacao> carregarTransacoes();

    List<Transacao> filtrarTransacoes(String data, String tipo, String categoria);

    boolean salvar(Transacao transacao);

    List<Transacao> buscarTodas(int idUsuario);

    List<Transacao> buscarUltimasTransacoes(int idUsuario, int limite);

    List<Transacao> filtrarTransacoes(int idUsuario, String dataInicio, String dataFim, String tipo, String categoria);
}




