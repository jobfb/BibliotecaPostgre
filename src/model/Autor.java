package model;

import java.time.LocalDate;

public class Autor {
    private Integer idAutor;
    private String nome;
    private String nacionalidade;
    private LocalDate dataNascimento;

    public Autor() { }

    public Autor(String nome, String nacionalidade, LocalDate dataNascimento) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdAutor() { return idAutor; }
    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    @Override
    public String toString() {
        return String.format("#%-4d %-30s Nacionalidade: %-15s Nascimento: %s",
                idAutor, nome, nacionalidade == null ? "-" : nacionalidade,
                dataNascimento == null ? "-" : dataNascimento);
    }
}
