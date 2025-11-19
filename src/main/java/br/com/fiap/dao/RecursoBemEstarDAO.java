package br.com.fiap.dao;

import br.com.fiap.to.RecursoBemEstarTO;
import java.sql.*;
import java.util.ArrayList;

/**
 * Gerencia todas as operações de persistência (CRUD) para a tabela de Recursos de Bem-Estar.
 */
public class RecursoBemEstarDAO {

    // Método auxiliar para mapear um ResultSet para RecursoBemEstarTO
    private RecursoBemEstarTO mapResultSetToTO(ResultSet rs) throws SQLException {
        RecursoBemEstarTO recurso = new RecursoBemEstarTO();
        recurso.setId(rs.getInt("ID_RECURSO"));
        recurso.setNome(rs.getString("NM_RECURSO"));
        recurso.setLink(rs.getString("DS_LINK"));
        recurso.setTipo(rs.getString("DS_TIPO"));
        return recurso;
    }

    /**
     * Salva um novo Recurso de Bem-Estar no banco de dados.
     */
    public RecursoBemEstarTO save(RecursoBemEstarTO recurso) {
        String sql = "INSERT INTO T_H_RECURSO_BEM_ESTAR (ID_RECURSO, NM_RECURSO, DS_LINK, DS_TIPO) " +
                "VALUES (T_H_RECURSO_BEM_ESTAR_SEQ.NEXTVAL, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[] { "ID_RECURSO" })) {

            ps.setString(1, recurso.getNome());
            ps.setString(2, recurso.getLink());
            ps.setString(3, recurso.getTipo());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        recurso.setId(rs.getInt(1));
                    }
                }
                return recurso;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar recurso no DAO: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca todos os Recursos de Bem-Estar cadastrados.
     */
    public ArrayList<RecursoBemEstarTO> findAll() {
        ArrayList<RecursoBemEstarTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_H_RECURSO_BEM_ESTAR ORDER BY NM_RECURSO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToTO(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os recursos no DAO: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca um Recurso de Bem-Estar pelo seu ID.
     */
    public RecursoBemEstarTO findById(int id) {
        RecursoBemEstarTO recurso = null;
        String sql = "SELECT * FROM T_H_RECURSO_BEM_ESTAR WHERE ID_RECURSO = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    recurso = mapResultSetToTO(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar recurso por ID no DAO: " + e.getMessage());
        }
        return recurso;
    }

    /**
     * Atualiza um Recurso de Bem-Estar existente.
     */
    public RecursoBemEstarTO update(RecursoBemEstarTO recurso) {
        String sql = "UPDATE T_H_RECURSO_BEM_ESTAR SET NM_RECURSO = ?, DS_LINK = ?, DS_TIPO = ? WHERE ID_RECURSO = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, recurso.getNome());
            ps.setString(2, recurso.getLink());
            ps.setString(3, recurso.getTipo());
            ps.setInt(4, recurso.getId());

            if (ps.executeUpdate() > 0) {
                return recurso;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar recurso no DAO: " + e.getMessage());
        }
        return null;
    }

    /**
     * Exclui um Recurso de Bem-Estar pelo seu ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM T_H_RECURSO_BEM_ESTAR WHERE ID_RECURSO = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir recurso no DAO: " + e.getMessage());
            return false;
        }
    }
}