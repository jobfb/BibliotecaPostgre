package dao;

import database.ConnectionFactory;
import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public Funcionario inserir(Funcionario f) throws SQLException {
        String sql = "INSERT INTO funcionario (nome, cpf, cargo, email, data_cont) VALUES (?, ?, ?, ?, ?) RETURNING id_funcionario";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCpf());
            ps.setString(3, f.getCargo());
            ps.setString(4, f.getEmail());
            if (f.getDataCont() != null) ps.setDate(5, Date.valueOf(f.getDataCont()));
            else ps.setNull(5, Types.DATE);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) f.setIdFuncionario(rs.getInt(1));
            }
        }
        return f;
    }

    public List<Funcionario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM funcionario ORDER BY id_funcionario";
        List<Funcionario> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Funcionario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean atualizar(Funcionario f) throws SQLException {
        String sql = "UPDATE funcionario SET nome=?, cpf=?, cargo=?, email=?, data_cont=? WHERE id_funcionario=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCpf());
            ps.setString(3, f.getCargo());
            ps.setString(4, f.getEmail());
            if (f.getDataCont() != null) ps.setDate(5, Date.valueOf(f.getDataCont()));
            else ps.setNull(5, Types.DATE);
            ps.setInt(6, f.getIdFuncionario());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Funcionario mapear(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setNome(rs.getString("nome"));
        f.setCpf(rs.getString("cpf"));
        f.setCargo(rs.getString("cargo"));
        f.setEmail(rs.getString("email"));
        Date d = rs.getDate("data_cont");
        f.setDataCont(d == null ? null : d.toLocalDate());
        return f;
    }
}
