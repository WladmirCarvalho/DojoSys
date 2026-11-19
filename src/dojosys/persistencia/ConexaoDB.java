package dojosys.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    // Lembre-se de alterar a senha para a do seu banco local no Windows/WSL
    private static final String URL = "jdbc:postgresql://localhost:5432/dojosys";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "sua_senha";

    public static Connection conectar() {
        try {
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com PostgreSQL estabelecida com sucesso!");
            return conexao;
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}
