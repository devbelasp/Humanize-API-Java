package br.com.fiap.dao;

import br.com.fiap.to.CheckinHumorTO;
import br.com.fiap.to.RelatorioHumorTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Gerencia o acesso a dados para o Questionário de Humor (T_H_HUMOR).
 * Inclui persistência (CREATE) e consultas agregadas (Análise para Dashboard).
 */
public class CheckinHumorDAO {

    // Método auxiliar para mapear um ResultSet para CheckinHumorTO
    private CheckinHumorTO mapResultSetToTO(ResultSet rs) throws SQLException {
        CheckinHumorTO checkin = new CheckinHumorTO();

        checkin.setId(rs.getInt("ID_HUMOR"));
        checkin.setFuncionarioId(rs.getInt("ID_FUNC"));
        checkin.setDataCheckin(rs.getDate("DT_CHECKIN").toLocalDate());

        // Mapeamento das 10 Perguntas (NR_ENERGIA, DS_SENTIMENTO, etc.)
        checkin.setNivelEnergia(rs.getInt("NR_ENERGIA"));
        checkin.setSentimento(rs.getString("DS_SENTIMENTO"));
        checkin.setVolumeDemandas(rs.getString("TP_VOLUME"));
        checkin.setBloqueios(rs.getString("DS_BLOQUEIO"));
        checkin.setDesconexao(rs.getString("TP_EQUILIBRIO_VT"));
        checkin.setNivelConexao(rs.getInt("NR_CONEXAO"));
        checkin.setQualidadeInteracao(rs.getString("TP_INTERACAO"));
        checkin.setQualidadeSono(rs.getString("TP_SONO"));
        checkin.setStatusPausas(rs.getString("TP_PAUSA"));
        checkin.setPequenoGanho(rs.getString("DS_PEQUENO_GANHO"));

        return checkin;
    }

    /**
     * Busca um check-in específico pelo ID do funcionário e data.
     */
    public CheckinHumorTO findByFuncionarioAndDate(int funcionarioId, LocalDate data) {
        CheckinHumorTO checkin = null;
        String sql = "SELECT * FROM T_H_HUMOR WHERE ID_FUNC = ? AND DT_CHECKIN = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, funcionarioId);
            ps.setTimestamp(2, Timestamp.valueOf(data.atStartOfDay()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    checkin = mapResultSetToTO(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar check-in por funcionário e data no DAO: " + e.getMessage());
        }
        return checkin;
    }

    /**
     * Salva um novo registro de Check-in de Humor com 10 perguntas.
     */
    public CheckinHumorTO save(CheckinHumorTO checkin) {

        String sql = "INSERT INTO T_H_HUMOR (ID_HUMOR, ID_FUNC, DT_CHECKIN, NR_ENERGIA, DS_SENTIMENTO, TP_VOLUME, DS_BLOQUEIO, TP_EQUILIBRIO_VT, NR_CONEXAO, TP_INTERACAO, TP_SONO, TP_PAUSA, DS_PEQUENO_GANHO) " +
                "VALUES (T_H_HUMOR_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        boolean sucesso = false;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, checkin.getFuncionarioId());
            ps.setTimestamp(2, Timestamp.valueOf(checkin.getDataCheckin().atStartOfDay()));

            ps.setInt(3, checkin.getNivelEnergia());
            ps.setString(4, checkin.getSentimento());
            ps.setString(5, checkin.getVolumeDemandas());
            ps.setString(6, checkin.getBloqueios());
            ps.setString(7, checkin.getDesconexao());
            ps.setInt(8, checkin.getNivelConexao());
            ps.setString(9, checkin.getQualidadeInteracao());
            ps.setString(10, checkin.getQualidadeSono());
            ps.setString(11, checkin.getStatusPausas());
            ps.setString(12, checkin.getPequenoGanho());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                sucesso = true;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        checkin.setId(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar check-in de humor no DAO: " + e.getMessage());
        }

        return sucesso ? checkin : null;
    }

    /**
     * Busca todo o histórico de check-ins de humor (dados brutos).
     */
    public ArrayList<CheckinHumorTO> findAll() {
        ArrayList<CheckinHumorTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_H_HUMOR ORDER BY DT_CHECKIN DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToTO(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os check-ins no DAO: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Realiza uma consulta SQL agregada para calcular a média de humor por equipe.
     */
    public ArrayList<RelatorioHumorTO> findMediaHumorPorEquipe() {
        ArrayList<RelatorioHumorTO> lista = new ArrayList<>();

        // A query usa o campo NR_ENERGIA para a média.
        String sql = "SELECT e.ID_EQUIPE, e.NM_EQUIPE, AVG(c.NR_ENERGIA) AS MEDIA_HUMOR, COUNT(c.ID_HUMOR) AS TOTAL_CHECKINS " +
                "FROM T_H_HUMOR c " +
                "JOIN T_H_FUNCIONARIO f ON c.ID_FUNC = f.ID_FUNC " +
                "JOIN T_H_EQUIPE e ON f.ID_EQUIPE = e.ID_EQUIPE " +
                "GROUP BY e.ID_EQUIPE, e.NM_EQUIPE " +
                "ORDER BY MEDIA_HUMOR DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RelatorioHumorTO relatorio = new RelatorioHumorTO(
                        rs.getInt("ID_EQUIPE"),
                        rs.getString("NM_EQUIPE"),
                        rs.getDouble("MEDIA_HUMOR"),
                        rs.getInt("TOTAL_CHECKINS")
                );
                lista.add(relatorio);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar média de humor por equipe no DAO: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Exclui todos os registros de check-in de um funcionário específico (antes de excluir o funcionário).
     */
    public void deleteByFuncionarioId(int funcionarioId) {
        String sql = "DELETE FROM T_H_HUMOR WHERE ID_FUNC = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, funcionarioId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir check-ins do funcionário no DAO: " + e.getMessage());
        }
    }
}