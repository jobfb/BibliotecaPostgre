package menu;

import dao.FuncionarioDAO;
import model.Funcionario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioMenu {
    private final FuncionarioDAO dao = new FuncionarioDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE FUNCIONARIOS");
            System.out.println("1 - Cadastrar novo funcionario");
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
        ConsoleUtil.titulo("Novo funcionario");
        String nome = ConsoleUtil.lerTextoObrigatorio("Nome");
        String cpf = ConsoleUtil.lerTextoObrigatorio("CPF");
        String cargo = ConsoleUtil.lerTextoObrigatorio("Cargo");
        String email = ConsoleUtil.lerTextoOpcional("E-mail");
        LocalDate admissao = ConsoleUtil.lerDataOpcional("Data de admissao");
        Funcionario f = dao.inserir(new Funcionario(nome, cpf, cargo, email, admissao));
        System.out.println("Funcionario cadastrado com sucesso! ID: " + f.getIdFuncionario());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Funcionarios cadastrados");
        List<Funcionario> lista = dao.listarTodos();
        for (Funcionario f : lista)
            System.out.println(f);
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do funcionario");
        Funcionario f = dao.buscarPorId(id);
        System.out.println(f == null ? "Funcionario nao encontrado." : f);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do funcionario a atualizar");
        Funcionario f = dao.buscarPorId(id);
        if (f == null) {
            System.out.println("Funcionario nao encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        String nome = ConsoleUtil.lerTexto("Nome [" + f.getNome() + "]");
        if (!nome.isEmpty())
            f.setNome(nome);
        String cpf = ConsoleUtil.lerTexto("CPF [" + f.getCpf() + "]");
        if (!cpf.isEmpty())
            f.setCpf(cpf);
        String cargo = ConsoleUtil.lerTexto("Cargo [" + f.getCargo() + "]");
        if (!cargo.isEmpty())
            f.setCargo(cargo);
        String email = ConsoleUtil.lerTexto("E-mail [" + f.getEmail() + "]");
        if (!email.isEmpty())
            f.setEmail(email);
        boolean ok = dao.atualizar(f);
        System.out.println(ok ? "Funcionario atualizado com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do funcionario a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Funcionario removido com sucesso!" : "Funcionario nao encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem emprestimos registrados por este funcionario.");
        }
    }
}
