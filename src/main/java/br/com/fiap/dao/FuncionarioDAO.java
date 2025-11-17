package br.com.fiap.dao;

import br.com.fiap.to.FuncionarioTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Gerencia o acesso a dados (CRUD) da entidade Funcionário (T_H_FUNCIONARIO).
 */
public class FuncionarioDAO {

    // Método auxiliar para mapear um ResultSet para FuncionarioTO
    private FuncionarioTO mapResultSetToTO(ResultSet rs) throws SQLException {
        FuncionarioTO funcionario = new FuncionarioTO();

        // Mapeamento das colunas da tabela T_H_FUNCIONARIO
        funcionario.setId(rs.getInt("ID_FUNC"));
        funcionario.setNome(rs.getString("NM_FUNCIONARIO"));
        funcionario.setEmail(rs.getString("EM_FUNCIONARIO"));
        funcionario.setSenha(rs.getString("DS_SENHA"));
        funcionario.setEquipeId(rs.getInt("ID_EQUIPE"));
        funcionario.setIdFuncao(rs.getInt("ID_FUNCAO"));

        if (rs.getDate("DT_CONTRATACAO") != null) {
            funcionario.setDataContratacao(
                    rs.getDate("DT_CONTRATACAO").toLocalDate()
            );
        }

        return funcionario;
    }

    /**
     * Busca um funcionário no banco de dados usando email e senha (para login).
     */
    public FuncionarioTO buscarPorLogin(String email, String senha) {
        FuncionarioTO funcionario = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM T_H_FUNCIONARIO WHERE EM_FUNCIONARIO = ? AND DS_SENHA = ?";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, senha);

            rs = ps.executeQuery();

            if (rs.next()) {
                funcionario = mapResultSetToTO(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por login no DAO: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }

        return funcionario;
    }

    /**
     * Busca um funcionário no banco de dados pelo seu e-mail.
     */
    public FuncionarioTO findByEmail(String email) {
        FuncionarioTO funcionario = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM T_H_FUNCIONARIO WHERE EM_FUNCIONARIO = ?";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                funcionario = mapResultSetToTO(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por e-mail no DAO: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }

        return funcionario;
    }

    /**
     * Salva um novo Funcionário no banco de dados, recuperando o ID gerado.
     */
    public FuncionarioTO save(FuncionarioTO funcionario) {

        String sql = "INSERT INTO T_H_FUNCIONARIO (ID_FUNC, NM_FUNCIONARIO, EM_FUNCIONARIO, DS_SENHA, DT_CONTRATACAO, ID_EQUIPE, ID_FUNCAO) " +
                "VALUES (T_H_FUNCIONARIO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean sucesso = false;


        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getSenha());
            ps.setDate(4, Date.valueOf(funcionario.getDataContratacao()));
            ps.setInt(5, funcionario.getEquipeId());
            ps.setInt(6, funcionario.getIdFuncao());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                sucesso = true;
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    funcionario.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar funcionário no DAO: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }

        return sucesso ? funcionario : null;
    }

    /**
     * Busca todos os funcionários cadastrados.
     */
    public ArrayList<FuncionarioTO> findAll() {
        ArrayList<FuncionarioTO> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM T_H_FUNCIONARIO ORDER BY ID_FUNC";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapResultSetToTO(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os funcionários no DAO: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }

        return lista;
    }

    /**
     * Busca um funcionário no banco de dados pelo seu ID.
     */
    public FuncionarioTO findByCodigo(int id) {
        FuncionarioTO funcionario = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM T_H_FUNCIONARIO WHERE ID_FUNC = ?";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                funcionario = mapResultSetToTO(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID no DAO: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }

        return funcionario;
    }

    /**
     * Atualiza um funcionário existente no banco de dados.
     */
    public FuncionarioTO update(FuncionarioTO funcionario) {

        String sql = "UPDATE T_H_FUNCIONARIO SET NM_FUNCIONARIO = ?, EM_FUNCIONARIO = ?, DS_SENHA = ?, DT_CONTRATACAO = ?, ID_EQUIPE = ?, ID_FUNCAO = ? WHERE ID_FUNC = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        boolean sucesso = false;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getSenha());
            ps.setDate(4, Date.valueOf(funcionario.getDataContratacao()));
            ps.setInt(5, funcionario.getEquipeId());
            ps.setInt(6, funcionario.getIdFuncao());
            ps.setInt(7, funcionario.getId());

            if (ps.executeUpdate() > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário no DAO: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }

        return sucesso ? funcionario : null;
    }

    /**
     * Exclui um funcionário no banco de dados pelo seu ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM T_H_FUNCIONARIO WHERE ID_FUNC = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir funcionário no DAO: " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.closeConnection();
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* log */ }
        }
    }

    /**
     * Exclui todos os registros da tabela associativa T_H_FUNC_RECURSO para um dado funcionário.
     */
    public void deleteRecursosAssociados(int funcionarioId) {
        String sql = "DELETE FROM T_H_FUNC_RECURSO WHERE ID_FUNC = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, funcionarioId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir recursos associados no DAO: " + e.getMessage());
        }
    }
}