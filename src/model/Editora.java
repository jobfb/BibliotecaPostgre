package model;

public class Editora {
    private Integer idEditora;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;

    public Editora() { }

    public Editora(String nome, String cnpj, String telefone, String email) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
    }

    public Integer getIdEditora() { return idEditora; }
    public void setIdEditora(Integer idEditora) { this.idEditora = idEditora; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("#%-4d %-35s CNPJ: %-20s Tel: %-16s Email: %s",
                idEditora, nome, cnpj == null ? "-" : cnpj,
                telefone == null ? "-" : telefone, email == null ? "-" : email);
    }
}
