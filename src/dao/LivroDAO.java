package dao;

import database.ConnectionFactory;
import model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public Livro inserir(Livro l) throws SQLException {
        String sql = "INSERT INTO livro (id_editora, id_autor, id_genero, titulo, ano_publicacao, num_paginas, idioma) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_livro";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, l.getIdEditora());
            ps.setInt(2, l.getIdAutor());
            ps.setInt(3, l.getIdGenero());
            ps.setString(4, l.getTitulo());
            setNullableInt(ps, 5, l.getAnoPublicacao());
            setNullableInt(ps, 6, l.getNumPaginas());
            ps.setString(7, l.getIdioma());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) l.setIdLivro(rs.getInt(1));
            }
        }
        return l;
    }

    public List<Livro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM livro ORDER BY id_livro";
        List<Livro> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Livro> listarTodosDetalhado() throws SQLException {
        String sql = "SELECT l.*, a.nome AS nome_autor, e.nome AS nome_editora, g.nome AS nome_genero " +
                "FROM livro l " +
                "JOIN autor a ON a.id_autor = l.id_autor " +
                "JOIN editora e ON e.id_editora = l.id_editora " +
                "JOIN genero g ON g.id_genero = l.id_genero " +
                "ORDER BY l.id_livro";
        List<Livro> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Livro l = mapear(rs);
                l.setNomeAutor(rs.getString("nome_autor"));
                l.setNomeEditora(rs.getString("nome_editora"));
                l.setNomeGenero(rs.getString("nome_genero"));
                lista.add(l);
            }
        }
        return lista;
    }

    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livro WHERE id_livro = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean atualizar(Livro l) throws SQLException {
        String sql = "UPDATE livro SET id_editora=?, id_autor=?, id_genero=?, titulo=?, ano_publicacao=?, num_paginas=?, idioma=? WHERE id_livro=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, l.getIdEditora());
            ps.setInt(2, l.getIdAutor());
            ps.setInt(3, l.getIdGenero());
            ps.setString(4, l.getTitulo());
            setNullableInt(ps, 5, l.getAnoPublicacao());
            setNullableInt(ps, 6, l.getNumPaginas());
            ps.setString(7, l.getIdioma());
            ps.setInt(8, l.getIdLivro());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM livro WHERE id_livro = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private void setNullableInt(PreparedStatement ps, int idx, Integer valor) throws SQLException {
        if (valor == null) ps.setNull(idx, Types.INTEGER);
        else ps.setInt(idx, valor);
    }

    private Livro mapear(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setIdLivro(rs.getInt("id_livro"));
        l.setIdEditora(rs.getInt("id_editora"));
        l.setIdAutor(rs.getInt("id_autor"));
        l.setIdGenero(rs.getInt("id_genero"));
        l.setTitulo(rs.getString("titulo"));
        int ano = rs.getInt("ano_publicacao");
        l.setAnoPublicacao(rs.wasNull() ? null : ano);
        int pag = rs.getInt("num_paginas");
        l.setNumPaginas(rs.wasNull() ? null : pag);
        l.setIdioma(rs.getString("idioma"));
        return l;
    }
}
