package util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;


public class InicializadorBanco {
    public static void inicializar() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT UNIQUE NOT NULL," +
                "senha TEXT NOT NULL," +
                "nome TEXT NOT NULL," +
                "telefone TEXT," +
                "cpf TEXT," +
                "data_nascimento TEXT" +
                ");";

        String sqlCategorias = "CREATE TABLE IF NOT EXISTS categorias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_usuario INTEGER NOT NULL," +
                "nome TEXT NOT NULL," +
                "FOREIGN KEY (id_usuario) REFERENCES usuarios(id)" +
                ");";

        String sqlFinancas = "CREATE TABLE IF NOT EXISTS financas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_usuario INTEGER NOT NULL," +
                "tipo TEXT NOT NULL," +
                "categoria TEXT," +
                "descricao TEXT," +
                "valor REAL NOT NULL," +
                "data TEXT," +
                "FOREIGN KEY (id_usuario) REFERENCES usuarios(id)" +
                ");";

        try (Connection conn = ConexaoSQLite.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuarios);
            stmt.execute(sqlCategorias);
            stmt.execute(sqlFinancas);

            System.out.println("Banco inicializado com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
