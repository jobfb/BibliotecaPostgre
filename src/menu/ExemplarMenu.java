package menu;

import dao.ExemplarDAO;
import model.Exemplar;

import java.sql.SQLException;
import java.util.List;

public class ExemplarMenu {
    private final ExemplarDAO dao = new ExemplarDAO();

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("CADASTRO DE EXEMPLARES");
            System.out.println("1 - Cadastrar novo exemplar");
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
        ConsoleUtil.titulo("Novo exemplar");
        int idLivro = ConsoleUtil.lerInt("ID do livro");
        String tombo = ConsoleUtil.lerTextoObrigatorio("Numero de tombamento");
        String conservacao = ConsoleUtil.lerTextoObrigatorio("Estado de conservacao (Novo/Bom/Regular/Danificado)");
        String localizacao = ConsoleUtil.lerTextoOpcional("Localizacao (estante/prateleira)");
        Exemplar ex = new Exemplar(idLivro, tombo, conservacao, localizacao, Exemplar.DISPONIVEL);
        dao.inserir(ex);
        System.out.println("Exemplar cadastrado com sucesso! ID: " + ex.getIdExemplar());
    }

    private void listar() throws SQLException {
        ConsoleUtil.titulo("Exemplares cadastrados");
        List<Exemplar> lista = dao.listarTodos();
        for (Exemplar e : lista)
            System.out.println(e);
        if (lista.isEmpty())
            System.out.println("(nenhum registro)");
    }

    private void buscar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do exemplar");
        Exemplar e = dao.buscarPorId(id);
        System.out.println(e == null ? "Exemplar nao encontrado." : e);
    }

    private void atualizar() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do exemplar a atualizar");
        Exemplar e = dao.buscarPorId(id);
        if (e == null) {
            System.out.println("Exemplar nao encontrado.");
            return;
        }
        System.out.println("Deixe em branco para manter o valor atual.");
        String tombo = ConsoleUtil.lerTexto("Numero de tombamento [" + e.getNumeroTombo() + "]");
        if (!tombo.isEmpty())
            e.setNumeroTombo(tombo);
        String conservacao = ConsoleUtil.lerTexto("Conservacao [" + e.getConservacao() + "]");
        if (!conservacao.isEmpty())
            e.setConservacao(conservacao);
        String local = ConsoleUtil.lerTexto("Localizacao [" + e.getLocalizacao() + "]");
        if (!local.isEmpty())
            e.setLocalizacao(local);
        String situacao = ConsoleUtil
                .lerTexto("Situacao [" + e.getSituacao() + "] (Disponivel/Emprestado/Em manutencao/Baixado)");
        if (!situacao.isEmpty())
            e.setSituacao(situacao);
        boolean ok = dao.atualizar(e);
        System.out.println(ok ? "Exemplar atualizado com sucesso!" : "Falha ao atualizar.");
    }

    private void remover() throws SQLException {
        int id = ConsoleUtil.lerInt("ID do exemplar a remover");
        try {
            boolean ok = dao.remover(id);
            System.out.println(ok ? "Exemplar removido com sucesso!" : "Exemplar nao encontrado.");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel remover: existem emprestimos registrados para este exemplar.");
        }
    }
}
