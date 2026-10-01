package menu;

import dao.RelatorioDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RelatorioMenu {
    private final RelatorioDAO dao = new RelatorioDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("RELATORIOS GERENCIAIS");
            System.out.println("1 - Livros mais emprestados em um periodo");
            System.out.println("2 - Usuarios com emprestimos em atraso");
            System.out.println("3 - Situacao do acervo por gênero");
            System.out.println("4 - Emprestimos atendidos por funcionario em um periodo");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            opcao = ConsoleUtil.lerOpcao();
            try {
                switch (opcao) {
                    case 1 -> relatorioLivrosMaisEmprestados();
                    case 2 -> relatorioUsuariosComAtraso();
                    case 3 -> relatorioSituacaoAcervo();
                    case 4 -> relatorioEmprestimosPorFuncionario();
                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            if (opcao != 0)
                ConsoleUtil.pausar();
        } while (opcao != 0);
    }

    private void relatorioLivrosMaisEmprestados() throws SQLException {
        ConsoleUtil.titulo("Relatorio: Livros mais emprestados no periodo");
        LocalDate inicio = ConsoleUtil.lerData("Data inicial");
        LocalDate fim = ConsoleUtil.lerData("Data final");
        List<String[]> linhas = dao.livrosMaisEmprestados(inicio, fim);
        ConsoleUtil.tabela(new String[] { "Titulo", "Autor", "Qtd. Emprestimos" }, linhas);
    }

    private void relatorioUsuariosComAtraso() throws SQLException {
        ConsoleUtil.titulo("Relatorio: Usuarios com emprestimos em atraso");
        List<String[]> linhas = dao.usuariosComAtraso();
        ConsoleUtil.tabela(new String[] { "Usuario", "Telefone", "Livro", "Previsao devolucao", "Atraso" }, linhas);
    }

    private void relatorioSituacaoAcervo() throws SQLException {
        ConsoleUtil.titulo("Relatorio: Situacao do acervo por gênero");
        List<String[]> linhas = dao.situacaoAcervoPorGenero();
        ConsoleUtil.tabela(new String[] { "Gênero", "Total exemplares", "Disponiveis", "Emprestados" }, linhas);
    }

    private void relatorioEmprestimosPorFuncionario() throws SQLException {
        ConsoleUtil.titulo("Relatorio: Emprestimos atendidos por funcionario no periodo");
        LocalDate inicio = ConsoleUtil.lerData("Data inicial");
        LocalDate fim = ConsoleUtil.lerData("Data final");
        List<String[]> linhas = dao.emprestimosPorFuncionario(inicio, fim);
        ConsoleUtil.tabela(new String[] { "Funcionario", "Qtd. Emprestimos", "Qtd. Atrasados" }, linhas);
    }
}
