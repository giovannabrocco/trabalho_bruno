package controller;
import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;
import view.TelaCadastroView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroController {


    private final TelaCadastroView telaCadastro;
    private final UsuarioDAO usuarioDAO;

    public CadastroController() {
        this.telaCadastro = new TelaCadastroView();
        this.usuarioDAO = new UsuarioDAOImpl();



        this.telaCadastro.adicionarListenerCadastrar(new CadastrarUsuarioListener());
        this.telaCadastro.adicionarListenerVoltar(new VoltarListener());

        this.telaCadastro.setVisible(true);
    }

    private class CadastrarUsuarioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = telaCadastro.getNome();
            String email = telaCadastro.getEmail();
            String cpf = telaCadastro.getCpf();
            String telefone = telaCadastro.getTelefone();
            String dataNascimento = telaCadastro.getDataNascimento();
            String senha = telaCadastro.getSenha();
            String confirmarSenha = telaCadastro.getConfirmarSenha();



            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                telaCadastro.exibirMensagem("Por favor, preencha todos os campos obrigatórios (Nome, Email, Senha, Confirmar Senha).");
                return;
            }
            if (!senha.equals(confirmarSenha)) {
                telaCadastro.exibirMensagem("As senhas digitadas não coincidem. Por favor, verifique.");
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                 telaCadastro.exibirMensagem("Por favor, insira um endereço de e-mail válido.");
                 return;
            }
            Usuario usuarioExistente = usuarioDAO.buscarPorEmail(email);
            if (usuarioExistente != null) {
                telaCadastro.exibirMensagem("Este endereço de e-mail já está cadastrado. Por favor, utilize outro e-mail ou faça login.");
                return;
            }




            Usuario novoUsuario = new Usuario(email, senha, nome, telefone, cpf, dataNascimento);
            try {
                usuarioDAO.salvar(novoUsuario);
                telaCadastro.exibirMensagem("Cadastro realizado com sucesso!");
                telaCadastro.dispose();
                new UsuarioController(); 


            } catch (Exception ex) {
                telaCadastro.exibirMensagem("Erro ao cadastrar usuário: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }



    private class VoltarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            telaCadastro.dispose();
            new UsuarioController();

        }
    }
}

