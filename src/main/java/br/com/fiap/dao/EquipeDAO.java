package br.com.fiap.dao;

import br.com.fiap.to.EquipeTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) para a entidade Equipe.
 * Implementa apenas a consulta de referência (findAll).
 */
public class EquipeDAO {

    /**
     * Retorna uma lista de todas as equipes cadastradas no sistema.
     * Os resultados são ordenados pelo nome da equipe (NM_EQUIPE).
     * @return ArrayList de EquipeTO, contendo todos os dados de ID, Nome, Sigla e Setor.
     */
    public ArrayList<EquipeTO> findAll() {
        ArrayList<EquipeTO> lista = new ArrayList<>();
        String sql = "SELECT ID_EQUIPE, NM_EQUIPE, SG_EQUIPE, DS_SETOR FROM T_H_EQUIPE ORDER BY NM_EQUIPE";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EquipeTO equipe = new EquipeTO();
                equipe.setId(rs.getInt("ID_EQUIPE"));
                equipe.setNome(rs.getString("NM_EQUIPE"));
                equipe.setSigla(rs.getString("SG_EQUIPE"));
                equipe.setSetor(rs.getString("DS_SETOR"));
                lista.add(equipe);
            }
        } catch (SQLException e) {
            // Em caso de erro na consulta, imprime no console de erro.
            System.err.println("Erro ao buscar todas as equipes: " + e.getMessage());
        }
        return lista;
    }
}