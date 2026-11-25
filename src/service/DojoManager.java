package service;

import database.ConexaoDB;
import model.Aluno;
import model.Instrutor;
import model.Praticante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DojoManager {
    private static DojoManager instancia;

    private DojoManager() {
    }

    public static DojoManager getInstancia() {
        if (instancia == null) {
            instancia = new DojoManager();
        }
        return instancia;
    }

    public void cadastrarMembro(Praticante praticante) {
        String sql = "INSERT INTO praticantes (nome, faixa, graus, tipo, mensalidade_em_dia, salario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                throw new IllegalStateException("Conexão com o banco de dados indisponível.");
            }

            stmt.setString(1, praticante.getNome());
            stmt.setString(2, praticante.getFaixa());
            stmt.setInt(3, praticante.getGraus());
            stmt.setString(4, praticante.getTipo());

            if (praticante instanceof Aluno aluno) {
                stmt.setBoolean(5, aluno.isMensalidadeEmDia());
                stmt.setDouble(6, 0.0);
            } else if (praticante instanceof Instrutor instrutor) {
                stmt.setBoolean(5, true);
                stmt.setDouble(6, instrutor.getSalario());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar membro no banco: " + e.getMessage(), e);
        }
    }

    public List<Praticante> listarPraticantes() {
        List<Praticante> lista = new ArrayList<>();
        String sql = "SELECT nome, faixa, graus, tipo, mensalidade_em_dia, salario FROM praticantes ORDER BY id";

        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (conn == null) {
                throw new IllegalStateException("Conexão com o banco de dados indisponível.");
            }

            while (rs.next()) {
                lista.add(montarPraticante(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar praticantes: " + e.getMessage(), e);
        }

        return lista;
    }

    public void listarTodos() {
        List<Praticante> membros = listarPraticantes();
        if (membros.isEmpty()) {
            System.out.println("Nenhum membro cadastrado no dojô.");
            return;
        }
        for (Praticante p : membros) {
            p.exibirResumo();
        }
    }

    public Praticante buscarPorNome(String nome) {
        String sql = "SELECT nome, faixa, graus, tipo, mensalidade_em_dia, salario FROM praticantes WHERE nome ILIKE ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                throw new IllegalStateException("Conexão com o banco de dados indisponível.");
            }

            stmt.setString(1, nome.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarPraticante(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar praticante: " + e.getMessage(), e);
        }
        return null;
    }

    public void promoverFaixa(String nome, String novaFaixa) {
        Praticante p = buscarPorNome(nome);
        if (p == null) {
            throw new IllegalArgumentException("Praticante não encontrado: " + nome);
        }

        p.promoverFaixa(novaFaixa);
        atualizarGraduacaoNoBanco(p);
    }

    public void adicionarGrau(String nome) {
        Praticante p = buscarPorNome(nome);
        if (p == null) {
            throw new IllegalArgumentException("Praticante não encontrado: " + nome);
        }

        p.adicionarGrau();
        atualizarGraduacaoNoBanco(p);
    }

    public void atualizarMensalidade(String nome, boolean emDia) {
        Praticante p = buscarPorNome(nome);
        if (p == null) {
            throw new IllegalArgumentException("Praticante não encontrado: " + nome);
        }
        if (!(p instanceof Aluno aluno)) {
            throw new IllegalArgumentException("Apenas alunos possuem controle de mensalidade.");
        }

        aluno.setMensalidadeEmDia(emDia);
        String sql = "UPDATE praticantes SET mensalidade_em_dia = ? WHERE nome ILIKE ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, aluno.isMensalidadeEmDia());
            stmt.setString(2, aluno.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar mensalidade: " + e.getMessage(), e);
        }
    }

    private void atualizarGraduacaoNoBanco(Praticante p) {
        String sql = "UPDATE praticantes SET faixa = ?, graus = ? WHERE nome ILIKE ?";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getFaixa());
            stmt.setInt(2, p.getGraus());
            stmt.setString(3, p.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar graduação no banco: " + e.getMessage(), e);
        }
    }

    private Praticante montarPraticante(ResultSet rs) throws SQLException {
        String nome = rs.getString("nome");
        String faixa = rs.getString("faixa");
        int graus = rs.getInt("graus");
        String tipo = rs.getString("tipo");

        if ("INSTRUTOR".equalsIgnoreCase(tipo)) {
            return new Instrutor(nome, faixa, graus, rs.getDouble("salario"));
        }
        return new Aluno(nome, faixa, graus, rs.getBoolean("mensalidade_em_dia"));
    }
}