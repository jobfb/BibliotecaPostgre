package menu;

import dao.EditoraDAO;
import model.Editora;

import java.sql.SQLException;
import java.util.List;

public class EditoraMenu {
    private final EditoraDAO dao = new EditoraDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE EDITORAS");
            System.out.println("1 - Cadastrar nova editora");
            System.out.println("2 - Listar todas");
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
        ConsoleUtil.titulo("Nova editora");
        String nome = ConsoleUtil.lerTextoObrigatorio("Nome");
        String cnpj = ConsoleUtil.lerTextoOpcional("CNPJ");
        String telefone = ConsoleUtil.lerTextoOpcional("Telefone");
        String email = ConsoleUtil.lerTextoOpcional("E-mail");
        Editora e = dao.inserir(new Editora(nome, cnpj, telefone, email));
        System.out.println("Editora cadastrada com sucesso! ID: " + e.getIdEditora());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Editoras cadastradas");
        List<Editora> lista = dao.listarTodos();
        for (Editora e : lista)
            System.out.println(e);
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID da editora");
        Editora e = dao.buscarPorId(id);
        System.out.println(e == null ? "Editora nao encontrada." : e);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID da editora a atualizar");
        Editora e = dao.buscarPorId(id);
        if (e == null) {
            System.out.println("Editora nao encontrada.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        String nome = ConsoleUtil.lerTexto("Nome [" + e.getNome() + "]");
        if (!nome.isEmpty())
            e.setNome(nome);
        String cnpj = ConsoleUtil.lerTexto("CNPJ [" + e.getCnpj() + "]");
        if (!cnpj.isEmpty())
            e.setCnpj(cnpj);
        String tel = ConsoleUtil.lerTexto("Telefone [" + e.getTelefone() + "]");
        if (!tel.isEmpty())
            e.setTelefone(tel);
        String email = ConsoleUtil.lerTexto("E-mail [" + e.getEmail() + "]");
        if (!email.isEmpty())
            e.setEmail(email);
        boolean ok = dao.atualizar(e);
        System.out.println(ok ? "Editora atualizada com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID da editora a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Editora removida com sucesso!" : "Editora nao encontrada.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem livros cadastrados para esta editora.");
        }
    }
}
