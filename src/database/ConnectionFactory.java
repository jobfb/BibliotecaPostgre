package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
  usuário "postgres" e senha "123456"
 */
public class ConnectionFactory {

    private static final String URL_PADRAO = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER_PADRAO = "postgres";
    private static final String SENHA_PADRAO = "123456";

    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL", URL_PADRAO);
        String user = System.getenv().getOrDefault("DB_USER", USER_PADRAO);
        String senha = System.getenv().getOrDefault("DB_PASSWORD", SENHA_PADRAO);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL nao encontrado.", e);
        }
        return DriverManager.getConnection(url, user, senha);
    }
}
