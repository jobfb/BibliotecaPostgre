package menu;

import dao.EmprestimoDAO;
import dao.ExemplarDAO;
import dao.LivroDAO;
import model.Emprestimo;
import model.Exemplar;
import model.Livro;

import java.sql.SQLException;
import java.util.List;

public class EmprestimoMenu {
    private static final int PRAZO_PADRAO_DIAS = 14;

    private final EmprestimoDAO dao = new EmprestimoDAO();
    private final ExemplarDAO exemplarDAO = new ExemplarDAO();
    private final LivroDAO livroDAO = new LivroDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("PROCESSO DE NEGOCIO: EMPRESTIMOS");
            System.out.println("1 - Realizar emprestimo");
            System.out.println("2 - Efetuar devolucao");
            System.out.println("3 - Listar todos os emprestimos");
            System.out.println("4 - Buscar emprestimo por ID");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            opcao = ConsoleUtil.lerOpcao();
            try {
                switch (opcao) {
                    case 1 -> realizarEmprestimo();
                    case 2 -> efetuarDevolucao();
                    case 3 -> listar();
                    case 4 -> buscar();
                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            if (opcao != 0)
                ConsoleUtil.pausar();
        } while (opcao != 0);
    }

    private void realizarEmprestimo() throws SQLException {
        ConsoleUtil.titulo("Realizar emprestimo");
        int idLivro = ConsoleUtil.lerInt("ID do livro desejado");
        Livro livro = livroDAO.buscarPorId(idLivro);
        if (livro == null) {
            System.out.println("Livro nao encontrado.");
            return;
        }

        List<Exemplar> disponiveis = exemplarDAO.listarDisponiveisPorLivro(idLivro);
        if (disponiveis.isEmpty()) {
            System.out.println("Nao ha exemplares disponiveis de \"" + livro.getTitulo() + "\" no momento.");
            return;
        }
        System.out.println("Exemplares disponiveis de \"" + livro.getTitulo() + "\":");
        for (Exemplar ex : disponiveis)
            System.out.println("  " + ex);

        int idExemplar = ConsoleUtil.lerInt("ID do exemplar a emprestar");
        int idUsuario = ConsoleUtil.lerInt("ID do usuario");
        int idFuncionario = ConsoleUtil.lerInt("ID do funcionario responsavel pelo atendimento");

        Emprestimo emp = new Emprestimo();
        emp.setIdExemplar(idExemplar);
        emp.setIdUsuario(idUsuario);
        emp.setIdFuncionario(idFuncionario);

        Emprestimo salvo = dao.realizarEmprestimo(emp, PRAZO_PADRAO_DIAS);
        System.out.println("Emprestimo realizado com sucesso! ID: " + salvo.getIdEmprestimo());
        System.out.println("Data do emprestimo: " + salvo.getDataEmp());
        System.out.println("Data prevista de devolucao: " + salvo.getDataDev() + " (prazo padrao de "
                + PRAZO_PADRAO_DIAS + " dias)");
    }

    private void efetuarDevolucao() throws SQLException {
        ConsoleUtil.titulo("Efetuar devolucao");
        int idEmprestimo = ConsoleUtil.lerInt("ID do emprestimo");
        long diasAtraso = dao.efetuarDevolucao(idEmprestimo);
        if (diasAtraso > 0) {
            System.out.println("Devolucao registrada. Atencao: devolucao com " + diasAtraso + " dia(s) de atraso.");
        } else {
            System.out.println("Devolucao registrada dentro do prazo. Obrigado!");
        }
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Emprestimos registrados");
        List<Emprestimo> lista = dao.listarTodos();
        for (Emprestimo e : lista) {
            System.out.printf(
                    "#%-4d Livro: %-30s | Usuario: %-20s | Funcionario: %-20s | Emprestimo: %-12s | Previsao: %-12s | Devolucao: %-12s | Situacao: %s%n",
                    e.getIdEmprestimo(), e.getTituloLivro(), e.getNomeUsuario(), e.getNomeFuncionario(),
                    e.getDataEmp(), e.getDataDev(),
                    e.getDataDevolucaoEfetiva() == null ? "-" : e.getDataDevolucaoEfetiva(), e.getSituacao());
        }
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do emprestimo");
        Emprestimo e = dao.buscarPorId(id);
        System.out.println(e == null ? "Emprestimo nao encontrado." : e);
    }
}
