package dojosys.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoDBTest {
    public static void main(String[] args) {
        System.out.println("Iniciando teste de conexão com o banco de dados...");
        Connection conn = ConexaoDB.conectar();
        
        // Teste unitário simples com assert puro (conforme o requisito obrigatório 3 do projeto)
        assert conn != null : "Erro: A conexão com o banco de dados falhou e retornou null.";
        
        if (conn != null) {
            System.out.println("Teste unitário de conexão finalizado com sucesso (assert passou)!");
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão de teste.");
            }
        }
    }
}
