package model;

import java.time.LocalDate;

public class Emprestimo {
    public static final String EM_ANDAMENTO = "Em andamento";
    public static final String DEVOLVIDO = "Devolvido";
    public static final String ATRASADO = "Atrasado";

    private Integer idEmprestimo;
    private Integer idExemplar;
    private Integer idUsuario;
    private Integer idFuncionario;
    private LocalDate dataEmp;
    private LocalDate dataDev;
    private LocalDate dataDevolucaoEfetiva;
    private String situacao;

    private String tituloLivro;
    private String nomeUsuario;
    private String nomeFuncionario;

    public Emprestimo() {
    }

    public Integer getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Integer idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Integer getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(Integer idExemplar) {
        this.idExemplar = idExemplar;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public LocalDate getDataEmp() {
        return dataEmp;
    }

    public void setDataEmp(LocalDate dataEmp) {
        this.dataEmp = dataEmp;
    }

    public LocalDate getDataDev() {
        return dataDev;
    }

    public void setDataDev(LocalDate dataDev) {
        this.dataDev = dataDev;
    }

    public LocalDate getDataDevolucaoEfetiva() {
        return dataDevolucaoEfetiva;
    }

    public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) {
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    @Override
    public String toString() {
        return String.format(
                "#%-4d Exemplar(id): %-4d Usuario(id): %-4d Funcionario(id): %-4d Emprestimo: %-12s Previsao: %-12s Devolucao: %-12s Situacao: %s",
                idEmprestimo, idExemplar, idUsuario, idFuncionario, dataEmp, dataDev,
                dataDevolucaoEfetiva == null ? "-" : dataDevolucaoEfetiva, situacao);
    }
}
