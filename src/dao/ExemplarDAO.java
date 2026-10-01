package dao;

import database.ConnectionFactory;
import model.Exemplar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExemplarDAO {

    public Exemplar inserir(Exemplar ex) throws SQLException {
        String sql = "INSERT INTO exemplar (id_livro, numero_tombo, conservacao, localizacao, situacao) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_exemplar";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ex.getIdLivro());
            ps.setString(2, ex.getNumeroTombo());
            ps.setString(3, ex.getConservacao());
            ps.setString(4, ex.getLocalizacao());
            ps.setString(5, ex.getSituacao() == null ? Exemplar.DISPONIVEL : ex.getSituacao());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) ex.setIdExemplar(rs.getInt(1));
            }
        }
        return ex;
    }

    public List<Exemplar> listarTodos() throws SQLException {
        String sql = "SELECT * FROM exemplar ORDER BY id_exemplar";
        List<Exemplar> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }


    public List<Exemplar> listarDisponiveisPorLivro(int idLivro) throws SQLException {
        String sql = "SELECT * FROM exemplar WHERE id_livro = ? AND situacao = 'Disponivel' ORDER BY id_exemplar";
        List<Exemplar> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLivro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Exemplar buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM exemplar WHERE id_exemplar = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean atualizar(Exemplar ex) throws SQLException {
        String sql = "UPDATE exemplar SET id_livro=?, numero_tombo=?, conservacao=?, localizacao=?, situacao=? WHERE id_exemplar=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ex.getIdLivro());
            ps.setString(2, ex.getNumeroTombo());
            ps.setString(3, ex.getConservacao());
            ps.setString(4, ex.getLocalizacao());
            ps.setString(5, ex.getSituacao());
            ps.setInt(6, ex.getIdExemplar());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean atualizarSituacao(int idExemplar, String situacao, Connection con) throws SQLException {
        String sql = "UPDATE exemplar SET situacao=? WHERE id_exemplar=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, situacao);
            ps.setInt(2, idExemplar);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM exemplar WHERE id_exemplar = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Exemplar mapear(ResultSet rs) throws SQLException {
        Exemplar ex = new Exemplar();
        ex.setIdExemplar(rs.getInt("id_exemplar"));
        ex.setIdLivro(rs.getInt("id_livro"));
        ex.setNumeroTombo(rs.getString("numero_tombo"));
        ex.setConservacao(rs.getString("conservacao"));
        ex.setLocalizacao(rs.getString("localizacao"));
        ex.setSituacao(rs.getString("situacao"));
        return ex;
    }
}
