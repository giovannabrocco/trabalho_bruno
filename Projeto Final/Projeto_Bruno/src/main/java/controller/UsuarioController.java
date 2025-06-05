package controller;
import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;
import util.Sessao;
import view.InterfaceView;
import view.TelaLoginView;
import view.TelaResumoFinanceiroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {

    private final TelaLoginView telaLogin;
    private final UsuarioDAO usuarioDAO;
    private InterfaceView interfaceView;
    private TransacaoController transacaoController;

    public UsuarioController() {
        this.telaLogin = new TelaLoginView();
        this.usuarioDAO = new UsuarioDAOImpl();

        this.telaLogin.adicionarListenerEntrar(new EntrarListener());
        this.telaLogin.adicionarListenerCadastrar(new CadastrarListener());

        // Torna a view visível
        this.telaLogin.setVisible(true);
    }

    private class EntrarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = telaLogin.getEmail();
            String senha = telaLogin.getSenha();

            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if (usuario != null && usuario.getSenha().equals(senha)) {
                Sessao.setUsuario(usuario.getId(), usuario.getEmail());
                telaLogin.dispose();

                transacaoController = new TransacaoController();
                interfaceView = new InterfaceView(transacaoController);

                interfaceView.adicionarBtnVerHistoricoListener(ev -> {
                    new HistoricoFinanceiroController(interfaceView);
                });

                interfaceView.adicionarBtnGerenciarListener(ev -> {
                    new GerenciadorFinanceiroController(interfaceView);
                });

                interfaceView.adicionarBtnResumoFinanceiroListener(ev -> {
                    TelaResumoFinanceiroView telaResumo = new TelaResumoFinanceiroView();
                    new ResumoFinanceiroController(telaResumo);
                    telaResumo.setVisible(true);
                });

                interfaceView.adicionarBtnSairListener(ev -> {
                    Sessao.limparSessao();
                    interfaceView.dispose();
                    new UsuarioController();
                });

                interfaceView.setVisible(true);

            } else {
                telaLogin.exibirMensagemErro("E-mail ou senha inválidos.");

            }
        }
    }

    private class CadastrarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            telaLogin.dispose();
            new CadastroController();
        }
    }
}

