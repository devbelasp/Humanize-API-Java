package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por estabelecer e gerenciar a conexão com o banco de dados Oracle.
 * Segue o padrão Factory e obtém as credenciais de conexão de variáveis de ambiente.
 */
public class ConnectionFactory {

    /**
     * Tenta estabelecer uma conexão com o banco de dados utilizando as variáveis de ambiente.
     * * @return Um objeto Connection válido.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados ou se o Driver não for encontrado.
     * @throws RuntimeException Se as variáveis de ambiente (DB_URL, DB_USER, DB_PASSWORD) não estiverem configuradas.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null) {
                throw new RuntimeException("Variáveis de ambiente não configuradas!");
            }

            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver Oracle não encontrado", e);
        }
    }
}