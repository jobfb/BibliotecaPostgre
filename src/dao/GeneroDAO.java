package dao;

import database.ConnectionFactory;
import model.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {

    public Genero inserir(Genero g) throws SQLException {
        String sql = "INSERT INTO genero (nome) VALUES (?) RETURNING id_genero";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, g.getNome());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) g.setIdGenero(rs.getInt(1));
            }
        }
        return g;
    }

    public List<Genero> listarTodos() throws SQLException {
        String sql = "SELECT * FROM genero ORDER BY id_genero";
        List<Genero> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Genero buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM genero WHERE id_genero = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean atualizar(Genero g) throws SQLException {
        String sql = "UPDATE genero SET nome=? WHERE id_genero=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, g.getNome());
            ps.setInt(2, g.getIdGenero());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM genero WHERE id_genero = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Genero mapear(ResultSet rs) throws SQLException {
        Genero g = new Genero();
        g.setIdGenero(rs.getInt("id_genero"));
        g.setNome(rs.getString("nome"));
        return g;
    }
}
