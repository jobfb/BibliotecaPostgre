package menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static String lerTexto(String rotulo) {
        System.out.print(rotulo + ": ");
        return sc.nextLine().trim();
    }

    public static String lerTextoObrigatorio(String rotulo) {
        String valor;
        do {
            valor = lerTexto(rotulo);
            if (valor.isEmpty())
                System.out.println("  >> Campo obrigatrio, tente novamente.");
        } while (valor.isEmpty());
        return valor;
    }

    public static String lerTextoOpcional(String rotulo) {
        String valor = lerTexto(rotulo + " (opcional, ENTER para pular)");
        return valor.isEmpty() ? null : valor;
    }

    public static int lerInt(String rotulo) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(rotulo));
            } catch (NumberFormatException e) {
                System.out.println("  >> Digite um numero inteiro valido.");
            }
        }
    }

    public static Integer lerIntOpcional(String rotulo) {
        String s = lerTexto(rotulo + " (opcional, ENTER para pular)");
        if (s.isEmpty())
            return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("  >> Valor invalido, ignorando.");
            return null;
        }
    }

    public static LocalDate lerData(String rotulo) {
        while (true) {
            String s = lerTexto(rotulo + " (dd/mm/aaaa)");
            try {
                return LocalDate.parse(s, FMT);
            } catch (Exception e) {
                System.out.println("  >> Data invalida. Use o formato dd/mm/aaaa.");
            }
        }
    }

    public static LocalDate lerDataOpcional(String rotulo) {
        String s = lerTexto(rotulo + " (dd/mm/aaaa, opcional, ENTER para pular)");
        if (s.isEmpty())
            return null;
        try {
            return LocalDate.parse(s, FMT);
        } catch (Exception e) {
            System.out.println("  >> Data invalida, ignorando.");
            return null;
        }
    }

    public static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    public static void titulo(String texto) {
        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println(" " + texto);
        System.out.println("=".repeat(70));
    }

    public static void tabela(String[] cabecalho, java.util.List<String[]> linhas) {
        int[] largura = new int[cabecalho.length];
        for (int i = 0; i < cabecalho.length; i++)
            largura[i] = cabecalho[i].length();
        for (String[] linha : linhas)
            for (int i = 0; i < linha.length; i++)
                largura[i] = Math.max(largura[i], linha[i] == null ? 1 : linha[i].length());

        imprimirLinha(cabecalho, largura);
        StringBuilder sep = new StringBuilder();
        for (int l : largura)
            sep.append("-".repeat(l + 2)).append("+");
        System.out.println(sep);
        for (String[] linha : linhas)
            imprimirLinha(linha, largura);
        if (linhas.isEmpty())
            System.out.println("(nenhum registro encontrado)");
    }

    private static void imprimirLinha(String[] linha, int[] largura) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linha.length; i++) {
            sb.append(" ").append(String.format("%-" + largura[i] + "s", linha[i] == null ? "-" : linha[i]))
                    .append(" |");
        }
        System.out.println(sb);
    }
}
