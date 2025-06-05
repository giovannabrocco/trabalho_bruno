package dao;
import model.Usuario;

public interface UsuarioDAO {

    void salvar(Usuario usuario);

    Usuario buscarPorEmail(String email);
}
