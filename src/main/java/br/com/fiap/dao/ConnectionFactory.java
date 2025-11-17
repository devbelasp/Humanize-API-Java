package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gerencia a conexão Singleton com o banco de dados Oracle.
 * A conexão utiliza credenciais carregadas de Variáveis de Ambiente (DB_URL, DB_USER, DB_PASSWORD).
 */
public class ConnectionFactory {

    private static Connection connection = null;

    /**
     * Fecha a conexão Singleton com o banco de dados.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = null;
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    /**
     * Retorna a conexão ativa ou estabelece uma nova, lendo credenciais das Variáveis de Ambiente.
     * @return Uma instância válida de {@code Connection}.
     * @throws RuntimeException se a conexão falhar ou as Variáveis de Ambiente não estiverem configuradas.
     */
    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("Variáveis de ambiente do banco (DB_URL, DB_USER, DB_PASSWORD) não configuradas!");
            }
            Connection conn = DriverManager.getConnection(url, user, password);

            conn.setAutoCommit(true);

            connection = conn;

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
            throw new RuntimeException("Falha ao conectar ao banco de dados.", e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro nome da classe: " + e.getMessage());
            throw new RuntimeException("Driver não encontrado.", e);
        }
        return connection;
    }
}