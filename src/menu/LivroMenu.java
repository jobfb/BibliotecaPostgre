package menu;

import dao.LivroDAO;
import model.Livro;

import java.sql.SQLException;
import java.util.List;

public class LivroMenu {
    private final LivroDAO dao = new LivroDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE LIVROS");
            System.out.println("1 - Cadastrar novo livro");
            System.out.println("2 - Listar todos (detalhado: autor/editora/gênero)");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            opcao = ConsoleUtil.lerOpcao();
            try {
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listar();
                    case 3 -> buscar();
                    case 4 -> atualizar();
                    case 5 -> remover();
                }
            } catch (SQLException e) {
                System.out.println("Erro de banco de dados: " + e.getMessage());
            }
            if (opcao != 0)
                ConsoleUtil.pausar();
        } while (opcao != 0);
    }

    private void cadastrar() throws SQLException {
        ConsoleUtil.titulo("Novo livro");
        System.out.println("Dica: cadastre antes a editora, o autor e o gênero, se ainda nao existirem.");
        int idEditora = ConsoleUtil.lerInt("ID da editora");
        int idAutor = ConsoleUtil.lerInt("ID do autor");
        int idGenero = ConsoleUtil.lerInt("ID do gênero");
        String titulo = ConsoleUtil.lerTextoObrigatorio("Titulo");
        Integer ano = ConsoleUtil.lerIntOpcional("Ano de publicacao");
        Integer paginas = ConsoleUtil.lerIntOpcional("Numero de paginas");
        String idioma = ConsoleUtil.lerTextoOpcional("Idioma");
        Livro l = new Livro(idEditora, idAutor, idGenero, titulo, ano, paginas, idioma);
        dao.inserir(l);
        System.out.println("Livro cadastrado com sucesso! ID: " + l.getIdLivro());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Livros cadastrados");
        List<Livro> lista = dao.listarTodosDetalhado();
        for (Livro l : lista) {
            System.out.printf("#%-4d %-40s | Autor: %-20s | Editora: %-20s | Gênero: %-15s | Ano: %s%n",
                    l.getIdLivro(), l.getTitulo(), l.getNomeAutor(), l.getNomeEditora(), l.getNomeGenero(),
                    l.getAnoPublicacao() == null ? "-" : l.getAnoPublicacao());
        }
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do livro");
        Livro l = dao.buscarPorId(id);
        System.out.println(l == null ? "Livro nao encontrado." : l);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do livro a atualizar");
        Livro l = dao.buscarPorId(id);
        if (l == null) {
            System.out.println("Livro nao encontrado.");
            return;
        }
        System.out.println("Deixe em branco / digite 0 para manter o valor atual quando aplicavel.");
        String titulo = ConsoleUtil.lerTexto("Titulo [" + l.getTitulo() + "]");
        if (!titulo.isEmpty())
            l.setTitulo(titulo);
        Integer idEditora = ConsoleUtil.lerIntOpcional("ID da editora [" + l.getIdEditora() + "]");
        if (idEditora != null)
            l.setIdEditora(idEditora);
        Integer idAutor = ConsoleUtil.lerIntOpcional("ID do autor [" + l.getIdAutor() + "]");
        if (idAutor != null)
            l.setIdAutor(idAutor);
        Integer idGenero = ConsoleUtil.lerIntOpcional("ID do gênero [" + l.getIdGenero() + "]");
        if (idGenero != null)
            l.setIdGenero(idGenero);
        Integer ano = ConsoleUtil.lerIntOpcional("Ano de publicacao [" + l.getAnoPublicacao() + "]");
        if (ano != null)
            l.setAnoPublicacao(ano);
        Integer paginas = ConsoleUtil.lerIntOpcional("Numero de paginas [" + l.getNumPaginas() + "]");
        if (paginas != null)
            l.setNumPaginas(paginas);
        String idioma = ConsoleUtil.lerTexto("Idioma [" + l.getIdioma() + "]");
        if (!idioma.isEmpty())
            l.setIdioma(idioma);
        boolean ok = dao.atualizar(l);
        System.out.println(ok ? "Livro atualizado com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do livro a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Livro removido com sucesso!" : "Livro nao encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem exemplares cadastrados para este livro.");
        }
    }
}
