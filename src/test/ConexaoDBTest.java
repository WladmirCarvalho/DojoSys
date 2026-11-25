package test;

import database.ConexaoDB;
import java.sql.Connection;

public class ConexaoDBTest {
    public static void main(String[] args) {
        System.out.println("Iniciando teste de conexão com o PostgreSQL...");
        try (Connection conn = ConexaoDB.conectar()) {
            assert conn != null : "Erro: a conexão retornou null.";
            assert !conn.isClosed() : "Erro: a conexão está fechada.";
            System.out.println("SUCESSO: ConexaoDBTest passou!");
        } catch (Exception e) {
            throw new AssertionError("Falha no teste de conexão: " + e.getMessage(), e);
        }
    }
}