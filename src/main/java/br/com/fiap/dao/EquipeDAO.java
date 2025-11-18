package br.com.fiap.dao;

import br.com.fiap.to.EquipeTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipeDAO {

    public ArrayList<EquipeTO> findAll() {
        ArrayList<EquipeTO> lista = new ArrayList<>();
        // ADICIONADO SG_EQUIPE NA QUERY
        String sql = "SELECT ID_EQUIPE, NM_EQUIPE, SG_EQUIPE, DS_SETOR FROM T_H_EQUIPE ORDER BY NM_EQUIPE";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EquipeTO equipe = new EquipeTO();
                equipe.setId(rs.getInt("ID_EQUIPE"));
                equipe.setNome(rs.getString("NM_EQUIPE"));
                equipe.setSigla(rs.getString("SG_EQUIPE")); // MAPEAMENTO NOVO
                equipe.setSetor(rs.getString("DS_SETOR"));
                lista.add(equipe);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as equipes: " + e.getMessage());
        }
        return lista;
    }
}