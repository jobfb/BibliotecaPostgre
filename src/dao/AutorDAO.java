package dao;

import database.ConnectionFactory;
import model.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    public Autor inserir(Autor a) throws SQLException {
        String sql = "INSERT INTO autor (nome, nacionalidade, data_nascimento) VALUES (?, ?, ?) RETURNING id_autor";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getNacionalidade());
            if (a.getDataNascimento() != null) ps.setDate(3, Date.valueOf(a.getDataNascimento()));
            else ps.setNull(3, Types.DATE);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) a.setIdAutor(rs.getInt(1));
            }
        }
        return a;
    }

    public List<Autor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM autor ORDER BY id_autor";
        List<Autor> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Autor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM autor WHERE id_autor = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean atualizar(Autor a) throws SQLException {
        String sql = "UPDATE autor SET nome=?, nacionalidade=?, data_nascimento=? WHERE id_autor=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getNacionalidade());
            if (a.getDataNascimento() != null) ps.setDate(3, Date.valueOf(a.getDataNascimento()));
            else ps.setNull(3, Types.DATE);
            ps.setInt(4, a.getIdAutor());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM autor WHERE id_autor = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Autor mapear(ResultSet rs) throws SQLException {
        Autor a = new Autor();
        a.setIdAutor(rs.getInt("id_autor"));
        a.setNome(rs.getString("nome"));
        a.setNacionalidade(rs.getString("nacionalidade"));
        Date d = rs.getDate("data_nascimento");
        a.setDataNascimento(d == null ? null : d.toLocalDate());
        return a;
    }
}
