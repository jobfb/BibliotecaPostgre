package model;

public class Genero {
    private Integer idGenero;
    private String nome;

    public Genero() { }

    public Genero(String nome) {
        this.nome = nome;
    }

    public Integer getIdGenero() { return idGenero; }
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("#%-4d %s", idGenero, nome);
    }
}
