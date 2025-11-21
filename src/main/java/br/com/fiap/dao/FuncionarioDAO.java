package br.com.fiap.dao;

import br.com.fiap.to.FuncionarioTO;
import java.sql.*;
import java.util.ArrayList;

/**
 * Data Access Object para a entidade Funcionário.
 * Gerencia todas as operações de persistência (CRUD) e autenticação na tabela T_H_FUNCIONARIO.
 */
public class FuncionarioDAO {

    /**
     * Método auxiliar para mapear um ResultSet para um objeto FuncionarioTO.
     * @param rs O ResultSet contendo os dados da consulta.
     * @return Um objeto FuncionarioTO preenchido.
     * @throws SQLException Se ocorrer erro ao acessar os dados do ResultSet.
     */
    private FuncionarioTO mapResultSetToTO(ResultSet rs) throws SQLException {
        FuncionarioTO funcionario = new FuncionarioTO();
        funcionario.setId(rs.getInt("ID_FUNC"));
        funcionario.setNome(rs.getString("NM_FUNCIONARIO"));
        funcionario.setEmail(rs.getString("EM_FUNCIONARIO"));
        funcionario.setSenha(rs.getString("DS_SENHA"));

        if (rs.getDate("DT_CONTRATACAO") != null) {
            funcionario.setDataContratacao(rs.getDate("DT_CONTRATACAO").toLocalDate());
        }

        funcionario.setEquipeId(rs.getInt("ID_EQUIPE"));
        funcionario.setIdFuncao(rs.getInt("ID_FUNCAO"));
        return funcionario;
    }

    /**
     * Realiza a autenticação de um funcionário verificando e-mail e senha.
     * @param email O e-mail do funcionário.
     * @param senha A senha do funcionário.
     * @return O objeto FuncionarioTO se as credenciais forem válidas, ou null caso contrário.
     */
    public FuncionarioTO buscarPorLogin(String email, String senha) {
        String sql = "SELECT * FROM T_H_FUNCIONARIO WHERE EM_FUNCIONARIO = ? AND DS_SENHA = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTO(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por login: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca um funcionário pelo seu endereço de e-mail (utilizado para validar unicidade).
     * @param email O e-mail a ser pesquisado.
     * @return O objeto FuncionarioTO encontrado ou null.
     */
    public FuncionarioTO findByEmail(String email) {
        String sql = "SELECT * FROM T_H_FUNCIONARIO WHERE EM_FUNCIONARIO = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTO(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por e-mail: " + e.getMessage());
        }
        return null;
    }

    /**
     * Salva um novo funcionário no banco de dados.
     * @param funcionario O objeto FuncionarioTO com os dados a serem salvos.
     * @return O objeto salvo com o ID gerado preenchido, ou null em caso de erro.
     */
    public FuncionarioTO save(FuncionarioTO funcionario) {
        String sql = "INSERT INTO T_H_FUNCIONARIO (ID_FUNC, NM_FUNCIONARIO, EM_FUNCIONARIO, DS_SENHA, DT_CONTRATACAO, ID_EQUIPE, ID_FUNCAO) " +
                "VALUES (T_H_FUNCIONARIO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[] { "ID_FUNC" })) {

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getSenha());
            ps.setDate(4, Date.valueOf(funcionario.getDataContratacao()));
            ps.setInt(5, funcionario.getEquipeId());
            ps.setInt(6, funcionario.getIdFuncao());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        funcionario.setId(rs.getInt(1));
                    }
                }
                return funcionario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar funcionário (SQL): " + e.getMessage());
        }
        return null;
    }

    /**
     * Retorna a lista de todos os funcionários cadastrados.
     * @return Uma lista de objetos FuncionarioTO.
     */
    public ArrayList<FuncionarioTO> findAll() {
        ArrayList<FuncionarioTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_H_FUNCIONARIO ORDER BY ID_FUNC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToTO(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca um funcionário pelo seu ID (Chave Primária).
     * @param id O ID do funcionário.
     * @return O objeto FuncionarioTO encontrado ou null.
     */
    public FuncionarioTO findByCodigo(int id) {
        String sql = "SELECT * FROM T_H_FUNCIONARIO WHERE ID_FUNC = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTO(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza os dados de um funcionário existente.
     * @param funcionario O objeto com os dados atualizados.
     * @return O objeto atualizado ou null em caso de falha.
     */
    public FuncionarioTO update(FuncionarioTO funcionario) {
        String sql = "UPDATE T_H_FUNCIONARIO SET NM_FUNCIONARIO = ?, EM_FUNCIONARIO = ?, DS_SENHA = ?, DT_CONTRATACAO = ?, ID_EQUIPE = ?, ID_FUNCAO = ? WHERE ID_FUNC = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getSenha());
            ps.setDate(4, Date.valueOf(funcionario.getDataContratacao()));
            ps.setInt(5, funcionario.getEquipeId());
            ps.setInt(6, funcionario.getIdFuncao());
            ps.setInt(7, funcionario.getId());

            if (ps.executeUpdate() > 0) {
                return funcionario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
        return null;
    }

    /**
     * Exclui um funcionário pelo seu ID.
     * @param id O ID do funcionário a ser excluído.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM T_H_FUNCIONARIO WHERE ID_FUNC = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exclui todos os recursos favoritos associados a um funcionário.
     * Utilizado para garantir a integridade referencial antes de excluir o funcionário.
     * @param funcionarioId O ID do funcionário.
     */
    public void deleteRecursosAssociados(int funcionarioId) {
        String sql = "DELETE FROM T_H_FUNC_RECURSO WHERE ID_FUNC = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, funcionarioId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir recursos associados: " + e.getMessage());
        }
    }
}