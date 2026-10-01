package dao;

import database.ConnectionFactory;
import model.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO {

    public Editora inserir(Editora e) throws SQLException {
        String sql = "INSERT INTO editora (nome, cnpj, telefone, email) VALUES (?, ?, ?, ?) RETURNING id_editora";
        try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getCnpj());
            ps.setString(3, e.getTelefone());
            ps.setString(4, e.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    e.setIdEditora(rs.getInt(1));
            }
        }
        return e;
    }

    public List<Editora> listarTodos() throws SQLException {
        String sql = "SELECT * FROM editora ORDER BY id_editora";
        List<Editora> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                lista.add(mapear(rs));
        }
        return lista;
    }

    public Editora buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM editora WHERE id_editora = ?";
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

    public boolean atualizar(Editora e) throws SQLException {
        String sql = "UPDATE editora SET nome=?, cnpj=?, telefone=?, email=? WHERE id_editora=?";
        try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getCnpj());
            ps.setString(3, e.getTelefone());
            ps.setString(4, e.getEmail());
            ps.setInt(5, e.getIdEditora());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM editora WHERE id_editora = ?";
        try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Editora mapear(ResultSet rs) throws SQLException {
        Editora e = new Editora();
        e.setIdEditora(rs.getInt("id_editora"));
        e.setNome(rs.getString("nome"));
        e.setCnpj(rs.getString("cnpj"));
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));
        return e;
    }
}
