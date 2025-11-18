package br.com.fiap.dao;

import br.com.fiap.to.RecursoBemEstarTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object para a tabela associativa T_H_FUNC_RECURSO.
 * Gerencia a lista de recursos de bem-estar selecionados (favoritos) por um funcionário.
 */
public class FuncionarioRecursoDAO {
    /**
     * Associa um recurso a um funcionário (adiciona à lista de favoritos).
     * @param idFunc ID do funcionário.
     * @param idRecurso ID do recurso de bem-estar.
     * @return true se a associação foi bem-sucedida, false caso contrário.
     */
    public boolean associarRecurso(int idFunc, int idRecurso) {
        String sql = "INSERT INTO T_H_FUNC_RECURSO (ID_FUNC, ID_RECURSO) VALUES (?, ?)";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFunc);
            ps.setInt(2, idRecurso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            // Se a associação já existir (chave duplicada), o Oracle lança exceção.
            System.err.println("Erro ao associar recurso ao funcionário (pode ser duplicidade): " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove a associação de um recurso a um funcionário (remove dos favoritos).
     * @param idFunc ID do funcionário.
     * @param idRecurso ID do recurso de bem-estar.
     * @return true se a remoção foi bem-sucedida, false caso contrário.
     */
    public boolean desassociarRecurso(int idFunc, int idRecurso) {
        String sql = "DELETE FROM T_H_FUNC_RECURSO WHERE ID_FUNC = ? AND ID_RECURSO = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFunc);
            ps.setInt(2, idRecurso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao desassociar recurso do funcionário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos os recursos de bem-estar selecionados por um funcionário.
     * @param idFunc ID do funcionário.
     * @return Uma lista de RecursoBemEstarTO.
     */
    public ArrayList<RecursoBemEstarTO> findRecursosByFuncionario(int idFunc) {
        ArrayList<RecursoBemEstarTO> lista = new ArrayList<>();

        String sql = "SELECT R.ID_RECURSO, R.NM_RECURSO, R.DS_LINK, R.DS_TIPO " +
                "FROM T_H_RECURSO_BEM_ESTAR R " +
                "JOIN T_H_FUNC_RECURSO FR ON R.ID_RECURSO = FR.ID_RECURSO " +
                "WHERE FR.ID_FUNC = ? " +
                "ORDER BY R.NM_RECURSO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFunc);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecursoBemEstarTO recurso = new RecursoBemEstarTO();

                    recurso.setId(rs.getInt("ID_RECURSO"));
                    recurso.setNome(rs.getString("NM_RECURSO"));
                    recurso.setLink(rs.getString("DS_LINK"));
                    recurso.setTipo(rs.getString("DS_TIPO"));

                    lista.add(recurso);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar recursos do funcionário: " + e.getMessage());
        }
        return lista;
    }
}