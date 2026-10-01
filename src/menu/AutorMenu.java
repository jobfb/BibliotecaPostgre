package menu;

import dao.AutorDAO;
import model.Autor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AutorMenu {
    private final AutorDAO dao = new AutorDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE AUTORES");
            System.out.println("1 - Cadastrar novo autor");
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
        ConsoleUtil.titulo("Novo autor");
        String nome = ConsoleUtil.lerTextoObrigatorio("Nome");
        String nacionalidade = ConsoleUtil.lerTextoOpcional("Nacionalidade");
        LocalDate nascimento = ConsoleUtil.lerDataOpcional("Data de nascimento");
        Autor a = dao.inserir(new Autor(nome, nacionalidade, nascimento));
        System.out.println("Autor cadastrado com sucesso! ID: " + a.getIdAutor());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Autores cadastrados");
        List<Autor> lista = dao.listarTodos();
        for (Autor a : lista)
            System.out.println(a);
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do autor");
        Autor a = dao.buscarPorId(id);
        System.out.println(a == null ? "Autor nao encontrado." : a);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do autor a atualizar");
        Autor a = dao.buscarPorId(id);
        if (a == null) {
            System.out.println("Autor nao encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        String nome = ConsoleUtil.lerTexto("Nome [" + a.getNome() + "]");
        if (!nome.isEmpty())
            a.setNome(nome);
        String nac = ConsoleUtil.lerTexto("Nacionalidade [" + a.getNacionalidade() + "]");
        if (!nac.isEmpty())
            a.setNacionalidade(nac);
        LocalDate nasc = ConsoleUtil.lerDataOpcional("Nova data de nascimento");
        if (nasc != null)
            a.setDataNascimento(nasc);
        boolean ok = dao.atualizar(a);
        System.out.println(ok ? "Autor atualizado com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do autor a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Autor removido com sucesso!" : "Autor nao encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem livros cadastrados para este autor.");
        }
    }
}
