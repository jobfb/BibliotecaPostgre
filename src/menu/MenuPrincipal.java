package menu;

public class MenuPrincipal {

    public void exibir() {
        int opcao;
        do {
            ConsoleUtil.titulo("SISTEMA DE GERENCIAMENTO DE BIBLIOTECA");
            System.out.println(" -- Cadastros  --");
            System.out.println("1 - Editoras");
            System.out.println("2 - Autores");
            System.out.println("3 - Gêneros");
            System.out.println("4 - Livros");
            System.out.println("5 - Exemplares");
            System.out.println("6 - Usuarios (leitores)");
            System.out.println("7 - Funcionarios");
            System.out.println(" -- Processos de negocio --");
            System.out.println("8 - Emprestimos (realizar / devolver)");
            System.out.println(" -- Relatorios --");
            System.out.println("9 - Relatorios gerenciais");
            System.out.println();
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = ConsoleUtil.lerOpcao();
            switch (opcao) {
                case 1 -> new EditoraMenu().exibir();
                case 2 -> new AutorMenu().exibir();
                case 3 -> new GeneroMenu().exibir();
                case 4 -> new LivroMenu().exibir();
                case 5 -> new ExemplarMenu().exibir();
                case 6 -> new UsuarioMenu().exibir();
                case 7 -> new FuncionarioMenu().exibir();
                case 8 -> new EmprestimoMenu().exibir();
                case 9 -> new RelatorioMenu().exibir();
                case 0 -> System.out.println("Encerrando o sistema!");
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

}
