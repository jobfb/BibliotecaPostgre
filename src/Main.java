import database.ConnectionFactory;
import menu.MenuPrincipal;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Verificando conexao com o banco de dados...");
        try (Connection con = ConnectionFactory.getConnection()) {
            System.out.println("Conexao estabelecida com sucesso: " + con.getCatalog());
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao banco de dados.");
            System.out.println("Detalhe: " + e.getMessage());
            System.out.println();
            System.out.println("Verifique se o PostgreSQL esta em execucao e se o banco 'biblioteca'");
            System.out.println("foi criado a partir dos scripts em sql/01_schema.sql e sql/02_dados_iniciais.sql.");
            System.out.println("As credenciais podem ser ajustadas em database/ConnectionFactory.java");
            System.out.println("ou pelas variaveis de ambiente DB_URL, DB_USER e DB_PASSWORD.");
            return;
        }
        new MenuPrincipal().exibir();
    }
}
