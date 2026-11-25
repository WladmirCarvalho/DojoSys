package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/dojosys";
    private static final String USUARIO = "postgres";
    // Usa variável de ambiente se existir; caso contrário, usa a senha padrão local
    private static final String SENHA = System.getenv().getOrDefault("DOJOSYS_DB_PASS", "1234");

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}