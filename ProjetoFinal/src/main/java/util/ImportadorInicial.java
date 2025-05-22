
package util;

import dao.*;
import model.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

public class ImportadorInicial {

    private static final String BASE_DIR = "Projeto Final";

    public static void importarSeNecessario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (!usuarioDAO.buscarTodos().isEmpty()) {
            System.out.println("Dados já foram importados anteriormente.");
            return;
        }

        importarUsuarios();
        importarCategorias();
        importarTransacoes();
        importarHistorico();
    }

    private static void importarUsuarios() {
        UsuarioDAO dao = new UsuarioDAO();
        Path path = Paths.get(BASE_DIR, "cadastros.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 2) {
                    dao.salvar(new Usuario(partes[0], partes[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao importar usuários: " + e.getMessage());
        }
    }

    private static void importarCategorias() {
        CategoriaFinanceiraDAO dao = new CategoriaFinanceiraDAO();
        Path path = Paths.get(BASE_DIR, "categorias.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                dao.salvar(new CategoriaFinanceira(linha));
            }
        } catch (IOException e) {
            System.err.println("Erro ao importar categorias: " + e.getMessage());
        }
    }

    private static void importarTransacoes() {
        TransacaoDAO dao = new TransacaoDAO();
        CategoriaFinanceiraDAO categoriaDAO = new CategoriaFinanceiraDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO(); // necessário agora

        Path path = Paths.get(BASE_DIR, "financas.txt");

        List<CategoriaFinanceira> categorias = categoriaDAO.buscarTodos();
        List<Usuario> usuarios = usuarioDAO.buscarTodos();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    String descricao = partes[0];
                    double valor = Double.parseDouble(partes[1]);
                    String data = partes[2];
                    String tipo = partes[3];

                    // Pegamos a primeira categoria e o primeiro usuário por simplicidade
                    CategoriaFinanceira cat = categorias.isEmpty() ? null : categorias.get(0);
                    Usuario usuario = usuarios.isEmpty() ? null : usuarios.get(0);

                    Transacao transacao = new Transacao(tipo,
                            cat != null ? cat.getNome() : "Padrão",
                            descricao,
                            data,
                            valor);
                    transacao.setUsuario(usuario);
                    dao.salvar(transacao);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao importar transações: " + e.getMessage());
        }
    }
    private static void importarHistorico() {
        HistoricoFinanceiroDAO dao = new HistoricoFinanceiroDAO();
        Path path = Paths.get(BASE_DIR, "historico_financas.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 2) {
                    dao.salvar(new HistoricoFinanceiro(partes[0], partes[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao importar histórico: " + e.getMessage());
        }
    }

}