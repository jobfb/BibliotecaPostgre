package model;

import java.time.LocalDate;

public class Funcionario {
    private Integer idFuncionario;
    private String nome;
    private String cpf;
    private String cargo;
    private String email;
    private LocalDate dataCont;

    public Funcionario() {
    }

    public Funcionario(String nome, String cpf, String cargo, String email, LocalDate dataCont) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.email = email;
        this.dataCont = dataCont;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataCont() {
        return dataCont;
    }

    public void setDataCont(LocalDate dataCont) {
        this.dataCont = dataCont;
    }

    @Override
    public String toString() {
        return String.format("#%-4d %-30s CPF: %-15s Cargo: %-22s Email: %-25s Admissao: %s",
                idFuncionario, nome, cpf, cargo, email == null ? "-" : email,
                dataCont == null ? "-" : dataCont);
    }
}
