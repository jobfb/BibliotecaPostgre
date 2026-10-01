package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<String[]> livrosMaisEmprestados(LocalDate inicio, LocalDate fim) throws SQLException {
        String sql = "SELECT l.titulo, a.nome AS autor, COUNT(*) AS qtd_emprestimos " +
                "FROM emprestimo e " +
                "JOIN exemplar ex ON ex.id_exemplar = e.id_exemplar " +
                "JOIN livro l ON l.id_livro = ex.id_livro " +
                "JOIN autor a ON a.id_autor = l.id_autor " +
                "WHERE e.data_emp BETWEEN ? AND ? " +
                "GROUP BY l.id_livro, l.titulo, a.nome " +
                "ORDER BY qtd_emprestimos DESC, l.titulo";
        List<String[]> linhas = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fim));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    linhas.add(new String[]{
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            String.valueOf(rs.getInt("qtd_emprestimos"))
                    });
                }
            }
        }
        return linhas;
    }

    public List<String[]> usuariosComAtraso() throws SQLException {
        new EmprestimoDAO().atualizarSituacoesAtrasadas();
        String sql = "SELECT u.nome AS usuario, u.telefone, l.titulo, e.data_dev, " +
                "(CURRENT_DATE - e.data_dev) AS dias_atraso " +
                "FROM emprestimo e " +
                "JOIN usuario u ON u.id_usuario = e.id_usuario " +
                "JOIN exemplar ex ON ex.id_exemplar = e.id_exemplar " +
                "JOIN livro l ON l.id_livro = ex.id_livro " +
                "WHERE e.situacao = 'Atrasado' " +
                "ORDER BY dias_atraso DESC";
        List<String[]> linhas = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                linhas.add(new String[]{
                        rs.getString("usuario"),
                        rs.getString("telefone") == null ? "-" : rs.getString("telefone"),
                        rs.getString("titulo"),
                        rs.getDate("data_dev").toString(),
                        rs.getInt("dias_atraso") + " dia(s)"
                });
            }
        }
        return linhas;
    }

    public List<String[]> situacaoAcervoPorGenero() throws SQLException {
        String sql = "SELECT g.nome AS genero, " +
                "COUNT(ex.id_exemplar) AS total, " +
                "COUNT(ex.id_exemplar) FILTER (WHERE ex.situacao = 'Disponivel') AS disponiveis, " +
                "COUNT(ex.id_exemplar) FILTER (WHERE ex.situacao = 'Emprestado') AS emprestados " +
                "FROM genero g " +
                "LEFT JOIN livro l ON l.id_genero = g.id_genero " +
                "LEFT JOIN exemplar ex ON ex.id_livro = l.id_livro " +
                "GROUP BY g.id_genero, g.nome " +
                "ORDER BY g.nome";
        List<String[]> linhas = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                linhas.add(new String[]{
                        rs.getString("genero"),
                        String.valueOf(rs.getInt("total")),
                        String.valueOf(rs.getInt("disponiveis")),
                        String.valueOf(rs.getInt("emprestados"))
                });
            }
        }
        return linhas;
    }

    public List<String[]> emprestimosPorFuncionario(LocalDate inicio, LocalDate fim) throws SQLException {
        String sql = "SELECT f.nome AS funcionario, COUNT(*) AS qtd, " +
                "COUNT(*) FILTER (WHERE e.situacao = 'Atrasado') AS qtd_atrasados " +
                "FROM emprestimo e " +
                "JOIN funcionario f ON f.id_funcionario = e.id_funcionario " +
                "WHERE e.data_emp BETWEEN ? AND ? " +
                "GROUP BY f.id_funcionario, f.nome " +
                "ORDER BY qtd DESC";
        List<String[]> linhas = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fim));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    linhas.add(new String[]{
                            rs.getString("funcionario"),
                            String.valueOf(rs.getInt("qtd")),
                            String.valueOf(rs.getInt("qtd_atrasados"))
                    });
                }
            }
        }
        return linhas;
    }
}
