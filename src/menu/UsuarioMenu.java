package menu;

import dao.UsuarioDAO;
import model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioMenu {
    private final UsuarioDAO dao = new UsuarioDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE USUARIOS (LEITORES)");
            System.out.println("1 - Cadastrar novo usuario");
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
        ConsoleUtil.titulo("Novo usuario");
        String nome = ConsoleUtil.lerTextoObrigatorio("Nome");
        String cpf = ConsoleUtil.lerTextoObrigatorio("CPF");
        String telefone = ConsoleUtil.lerTextoOpcional("Telefone");
        String email = ConsoleUtil.lerTextoOpcional("E-mail");
        Usuario u = dao.inserir(new Usuario(nome, cpf, telefone, email));
        System.out.println("Usuario cadastrado com sucesso! ID: " + u.getIdUsuario());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Usuarios cadastrados");
        List<Usuario> lista = dao.listarTodos();
        for (Usuario u : lista)
            System.out.println(u);
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do usuario");
        Usuario u = dao.buscarPorId(id);
        System.out.println(u == null ? "Usuario nao encontrado." : u);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do usuario a atualizar");
        Usuario u = dao.buscarPorId(id);
        if (u == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        String nome = ConsoleUtil.lerTexto("Nome [" + u.getNome() + "]");
        if (!nome.isEmpty())
            u.setNome(nome);
        String cpf = ConsoleUtil.lerTexto("CPF [" + u.getCpf() + "]");
        if (!cpf.isEmpty())
            u.setCpf(cpf);
        String tel = ConsoleUtil.lerTexto("Telefone [" + u.getTelefone() + "]");
        if (!tel.isEmpty())
            u.setTelefone(tel);
        String email = ConsoleUtil.lerTexto("E-mail [" + u.getEmail() + "]");
        if (!email.isEmpty())
            u.setEmail(email);
        boolean ok = dao.atualizar(u);
        System.out.println(ok ? "Usuario atualizado com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do usuario a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Usuario removido com sucesso!" : "Usuario nao encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem emprestimos vinculados a este usuario.");
        }
    }
}
