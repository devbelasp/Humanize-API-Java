package br.com.fiap.dao;

import br.com.fiap.to.FuncaoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object para a entidade Função.
 * Implementa a consulta (findAll) para as tabelas de referência de perfis.
 */
public class FuncaoDAO {

    /**
     * Retorna uma lista de todas as funções cadastradas no sistema.
     * @return ArrayList de FuncaoTO.
     */
    public ArrayList<FuncaoTO> findAll() {
        ArrayList<FuncaoTO> lista = new ArrayList<>();
        String sql = "SELECT ID_FUNCAO, NM_FUNCAO FROM T_H_FUNCAO ORDER BY NM_FUNCAO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FuncaoTO funcao = new FuncaoTO();

                // Mapeamento das colunas para o TO
                funcao.setId(rs.getInt("ID_FUNCAO"));
                funcao.setNome(rs.getString("NM_FUNCAO"));

                lista.add(funcao);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as funções: " + e.getMessage());
            // Em caso de falha, retorna a lista vazia
            return new ArrayList<>();
        }

        return lista;
    }
}