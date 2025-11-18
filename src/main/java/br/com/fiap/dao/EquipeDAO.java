package br.com.fiap.dao;

import br.com.fiap.to.EquipeTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object para a entidade Equipe.
 * Implementa apenas a consulta (findAll) para as tabelas de referência.
 */
public class EquipeDAO {

    /**
     * Retorna uma lista de todas as equipes cadastradas no sistema.
     * @return ArrayList de EquipeTO.
     */
    public ArrayList<EquipeTO> findAll() {
        ArrayList<EquipeTO> lista = new ArrayList<>();
        String sql = "SELECT ID_EQUIPE, NM_EQUIPE, DS_SETOR FROM T_H_EQUIPE ORDER BY NM_EQUIPE";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EquipeTO equipe = new EquipeTO();

                // Mapeamento das colunas para o TO
                equipe.setId(rs.getInt("ID_EQUIPE"));
                equipe.setNome(rs.getString("NM_EQUIPE"));
                equipe.setSetor(rs.getString("DS_SETOR"));

                lista.add(equipe);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as equipes: " + e.getMessage());
            // Em caso de falha, retorna a lista vazia
            return new ArrayList<>();
        }

        return lista;
    }
}