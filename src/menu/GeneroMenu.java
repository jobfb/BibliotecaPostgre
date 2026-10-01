package menu;

import dao.GeneroDAO;
import model.Genero;

import java.sql.SQLException;
import java.util.List;

public class GeneroMenu {
    private final GeneroDAO dao = new GeneroDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE GÊNEROS");
            System.out.println("1 - Cadastrar novo gênero");
            System.out.println("2 - Listar todos");
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
        ConsoleUtil.titulo("Novo gênero");
        String nome = ConsoleUtil.lerTextoObrigatorio("Nome");
        Genero g = dao.inserir(new Genero(nome));
        System.out.println("Gênero cadastrado com sucesso! ID: " + g.getIdGenero());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Gêneros cadastrados");
        List<Genero> lista = dao.listarTodos();
        for (Genero g : lista)
            System.out.println(g);
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do gênero");
        Genero g = dao.buscarPorId(id);
        System.out.println(g == null ? "Gênero nao encontrado." : g);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do gênero a atualizar");
        Genero g = dao.buscarPorId(id);
        if (g == null) {
            System.out.println("Gênero nao encontrado.");
            return;
        }
        String nome = ConsoleUtil.lerTextoObrigatorio("Novo nome [" + g.getNome() + "]");
        g.setNome(nome);
        boolean ok = dao.atualizar(g);
        System.out.println(ok ? "Gênero atualizado com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do gênero a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Gênero removido com sucesso!" : "Gênero nao encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem livros cadastrados com este gênero.");
        }
    }
}
