
import dao.TransacaoDAO;
import model.Transacao;
import model.Usuario;
import model.CategoriaFinanceira;

import java.util.List;

public class Financeiro {

    private Usuario usuario;
    private TransacaoDAO dao;

    public Financeiro(Usuario usuario) {
        this.usuario = usuario;
        this.dao = new TransacaoDAO();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacao.setUsuario(usuario);
        dao.salvar(transacao);
    }

    public List<Transacao> getTransacoes() {
        return dao.buscarTodos(usuario);
    }

    public double calcularSaldo() {
        double saldo = 0.0;
        for (Transacao t : getTransacoes()) {
            if ("Receita".equalsIgnoreCase(t.getTipo())) {
                saldo += t.getValor();
            } else if ("Despesa".equalsIgnoreCase(t.getTipo())) {
                saldo -= t.getValor();
            }
        }
        return saldo;
    }

    public double calcularTotalReceitas() {
        return getTransacoes().stream()
                .filter(t -> "Receita".equalsIgnoreCase(t.getTipo()))
                .mapToDouble(Transacao::getValor)
                .sum();
    }

    public double calcularTotalDespesas() {
        return getTransacoes().stream()
                .filter(t -> "Despesa".equalsIgnoreCase(t.getTipo()))
                .mapToDouble(Transacao::getValor)
                .sum();
    }
}
