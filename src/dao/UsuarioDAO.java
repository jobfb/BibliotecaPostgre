package dao;

import database.ConnectionFactory;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario inserir(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nome, cpf, telefone, email) VALUES (?, ?, ?, ?) RETURNING id_usuario, data_cadastro";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getTelefone());
            ps.setString(4, u.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u.setIdUsuario(rs.getInt(1));
                    u.setDataCadastro(rs.getDate(2).toLocalDate());
                }
            }
        }
        return u;
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuario ORDER BY id_usuario";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=? WHERE id_usuario=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getTelefone());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));
        u.setTelefone(rs.getString("telefone"));
        u.setEmail(rs.getString("email"));
        Date d = rs.getDate("data_cadastro");
        u.setDataCadastro(d == null ? null : d.toLocalDate());
        return u;
    }
}
