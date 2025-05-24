package controller;

import dao.UsuarioDAO;
import model.Usuario;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Sessao;


public class UsuarioController {
    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
            return false;
        }
        usuarioDAO.salvar(usuario);
        return true;
    }

    public boolean fazerLogin(String email, String senha) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            Sessao.setUsuario(usuario.getId(), usuario.getEmail()); // ✅ Correto
            // <- isso é essencial
            return true;
        }
        return false;
    }


    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }
}
