package model;

public class Exemplar {
    public static final String DISPONIVEL = "Disponivel";
    public static final String EMPRESTADO = "Emprestado";
    public static final String EM_MANUTENCAO = "Em manutencao";
    public static final String BAIXADO = "Baixado";

    private Integer idExemplar;
    private Integer idLivro;
    private String numeroTombo;
    private String conservacao;
    private String localizacao;
    private String situacao;

    private String tituloLivro;

    public Exemplar() {
    }

    public Exemplar(Integer idLivro, String numeroTombo, String conservacao, String localizacao, String situacao) {
        this.idLivro = idLivro;
        this.numeroTombo = numeroTombo;
        this.conservacao = conservacao;
        this.localizacao = localizacao;
        this.situacao = situacao;
    }

    public Integer getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(Integer idExemplar) {
        this.idExemplar = idExemplar;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getNumeroTombo() {
        return numeroTombo;
    }

    public void setNumeroTombo(String numeroTombo) {
        this.numeroTombo = numeroTombo;
    }

    public String getConservacao() {
        return conservacao;
    }

    public void setConservacao(String conservacao) {
        this.conservacao = conservacao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
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

    @Override
    public String toString() {
        return String.format("#%-4d Tombo: %-10s Livro(id): %-4d Conservacao: %-10s Local: %-15s Situacao: %s",
                idExemplar, numeroTombo, idLivro, conservacao,
                localizacao == null ? "-" : localizacao, situacao);
    }
}
