package dao;

import database.ConnectionFactory;
import model.Emprestimo;
import model.Exemplar;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    private final ExemplarDAO exemplarDAO = new ExemplarDAO();

    public Emprestimo realizarEmprestimo(Emprestimo emp, int prazoDias) throws SQLException {
        String sqlInsert = "INSERT INTO emprestimo (id_exemplar, id_usuario, id_funcionario, data_emp, data_dev, situacao) "
                +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_emprestimo";
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);
            @SuppressWarnings("unused")
            Exemplar exemplar;
            try (PreparedStatement lock = con.prepareStatement(
                    "SELECT situacao FROM exemplar WHERE id_exemplar = ? FOR UPDATE")) {
                lock.setInt(1, emp.getIdExemplar());
                try (ResultSet rs = lock.executeQuery()) {
                    if (!rs.next())
                        throw new SQLException("Exemplar nao encontrado.");
                    if (!Exemplar.DISPONIVEL.equals(rs.getString("situacao"))) {
                        throw new SQLException("Exemplar nao esta disponivel para emprestimo (situacao atual: "
                                + rs.getString("situacao") + ").");
                    }
                }
            }

            LocalDate dataEmp = LocalDate.now();
            LocalDate dataDev = dataEmp.plusDays(prazoDias);

            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setInt(1, emp.getIdExemplar());
                ps.setInt(2, emp.getIdUsuario());
                ps.setInt(3, emp.getIdFuncionario());
                ps.setDate(4, Date.valueOf(dataEmp));
                ps.setDate(5, Date.valueOf(dataDev));
                ps.setString(6, Emprestimo.EM_ANDAMENTO);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        emp.setIdEmprestimo(rs.getInt(1));
                }
            }

            exemplarDAO.atualizarSituacao(emp.getIdExemplar(), Exemplar.EMPRESTADO, con);

            con.commit();
            emp.setDataEmp(dataEmp);
            emp.setDataDev(dataDev);
            emp.setSituacao(Emprestimo.EM_ANDAMENTO);
            return emp;
        } catch (SQLException e) {
            if (con != null)
                con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public long efetuarDevolucao(int idEmprestimo) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);

            int idExemplar;
            LocalDate dataDev;
            try (PreparedStatement lock = con.prepareStatement(
                    "SELECT id_exemplar, data_dev, situacao FROM emprestimo WHERE id_emprestimo = ? FOR UPDATE")) {
                lock.setInt(1, idEmprestimo);
                try (ResultSet rs = lock.executeQuery()) {
                    if (!rs.next())
                        throw new SQLException("Emprestimo nao encontrado.");
                    if (Emprestimo.DEVOLVIDO.equals(rs.getString("situacao"))) {
                        throw new SQLException("Este emprestimo ja foi devolvido.");
                    }
                    idExemplar = rs.getInt("id_exemplar");
                    dataDev = rs.getDate("data_dev").toLocalDate();
                }
            }

            LocalDate hoje = LocalDate.now();
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE emprestimo SET data_devolucao_efetiva=?, situacao=? WHERE id_emprestimo=?")) {
                ps.setDate(1, Date.valueOf(hoje));
                ps.setString(2, Emprestimo.DEVOLVIDO);
                ps.setInt(3, idEmprestimo);
                ps.executeUpdate();
            }

            exemplarDAO.atualizarSituacao(idExemplar, Exemplar.DISPONIVEL, con);
            con.commit();

            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataDev, hoje);
            return Math.max(diasAtraso, 0);
        } catch (SQLException e) {
            if (con != null)
                con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public int atualizarSituacoesAtrasadas() throws SQLException {
        String sql = "UPDATE emprestimo SET situacao = ? WHERE situacao = ? AND data_dev < CURRENT_DATE";
        try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, Emprestimo.ATRASADO);
            ps.setString(2, Emprestimo.EM_ANDAMENTO);
            return ps.executeUpdate();
        }
    }

    public List<Emprestimo> listarTodos() throws SQLException {
        atualizarSituacoesAtrasadas();
        String sql = "SELECT e.*, l.titulo, u.nome AS nome_usuario, f.nome AS nome_funcionario " +
                "FROM emprestimo e " +
                "JOIN exemplar ex ON ex.id_exemplar = e.id_exemplar " +
                "JOIN livro l ON l.id_livro = ex.id_livro " +
                "JOIN usuario u ON u.id_usuario = e.id_usuario " +
                "JOIN funcionario f ON f.id_funcionario = e.id_funcionario " +
                "ORDER BY e.id_emprestimo";
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                lista.add(mapear(rs));
        }
        return lista;
    }

    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT e.*, l.titulo, u.nome AS nome_usuario, f.nome AS nome_funcionario " +
                "FROM emprestimo e " +
                "JOIN exemplar ex ON ex.id_exemplar = e.id_exemplar " +
                "JOIN livro l ON l.id_livro = ex.id_livro " +
                "JOIN usuario u ON u.id_usuario = e.id_usuario " +
                "JOIN funcionario f ON f.id_funcionario = e.id_funcionario " +
                "WHERE e.id_emprestimo = ?";
        try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapear(rs);
            }
        }
        return null;
    }

    private Emprestimo mapear(ResultSet rs) throws SQLException {
        Emprestimo e = new Emprestimo();
        e.setIdEmprestimo(rs.getInt("id_emprestimo"));
        e.setIdExemplar(rs.getInt("id_exemplar"));
        e.setIdUsuario(rs.getInt("id_usuario"));
        e.setIdFuncionario(rs.getInt("id_funcionario"));
        e.setDataEmp(rs.getDate("data_emp").toLocalDate());
        e.setDataDev(rs.getDate("data_dev").toLocalDate());
        Date dEfetiva = rs.getDate("data_devolucao_efetiva");
        e.setDataDevolucaoEfetiva(dEfetiva == null ? null : dEfetiva.toLocalDate());
        e.setSituacao(rs.getString("situacao"));
        e.setTituloLivro(rs.getString("titulo"));
        e.setNomeUsuario(rs.getString("nome_usuario"));
        e.setNomeFuncionario(rs.getString("nome_funcionario"));
        return e;
    }
}
