package model;

public class Livro {
    private Integer idLivro;
    private Integer idEditora;
    private Integer idAutor;
    private Integer idGenero;
    private String titulo;
    private Integer anoPublicacao;
    private Integer numPaginas;
    private String idioma;

    private String nomeEditora;
    private String nomeAutor;
    private String nomeGenero;

    public Livro() { }

    public Livro(Integer idEditora, Integer idAutor, Integer idGenero, String titulo,
                 Integer anoPublicacao, Integer numPaginas, String idioma) {
        this.idEditora = idEditora;
        this.idAutor = idAutor;
        this.idGenero = idGenero;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.numPaginas = numPaginas;
        this.idioma = idioma;
    }

    public Integer getIdLivro() { return idLivro; }
    public void setIdLivro(Integer idLivro) { this.idLivro = idLivro; }

    public Integer getIdEditora() { return idEditora; }
    public void setIdEditora(Integer idEditora) { this.idEditora = idEditora; }

    public Integer getIdAutor() { return idAutor; }
    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; }

    public Integer getIdGenero() { return idGenero; }
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public Integer getNumPaginas() { return numPaginas; }
    public void setNumPaginas(Integer numPaginas) { this.numPaginas = numPaginas; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String getNomeEditora() { return nomeEditora; }
    public void setNomeEditora(String nomeEditora) { this.nomeEditora = nomeEditora; }

    public String getNomeAutor() { return nomeAutor; }
    public void setNomeAutor(String nomeAutor) { this.nomeAutor = nomeAutor; }

    public String getNomeGenero() { return nomeGenero; }
    public void setNomeGenero(String nomeGenero) { this.nomeGenero = nomeGenero; }

    @Override
    public String toString() {
        return String.format("#%-4d %-45s Autor(id): %-4d Editora(id): %-4d Genero(id): %-4d Ano: %-6s Idioma: %s",
                idLivro, titulo, idAutor, idEditora, idGenero,
                anoPublicacao == null ? "-" : anoPublicacao, idioma == null ? "-" : idioma);
    }
}
