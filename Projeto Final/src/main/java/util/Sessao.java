package util;



public class Sessao {
    private static int idUsuarioLogado;
    private static String emailUsuario;

    public static void setUsuario(int id, String email) {
        idUsuarioLogado = id;
        emailUsuario = email;
    }

    public static int getIdUsuario() {
        return idUsuarioLogado;
    }

    public static String getEmailUsuario() {
        return emailUsuario;
    }

    public static void limparSessao() {
        idUsuarioLogado = 0;
        emailUsuario = null;
    }
}
