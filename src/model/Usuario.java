package model;

import java.time.LocalDate;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataCadastro;

    public Usuario() { }

    public Usuario(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    @Override
    public String toString() {
        return String.format("#%-4d %-30s CPF: %-15s Tel: %-16s Email: %-25s Cadastro: %s",
                idUsuario, nome, cpf, telefone == null ? "-" : telefone,
                email == null ? "-" : email, dataCadastro);
    }
}
