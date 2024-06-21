import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        //Inserir todos os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remover o funcionário “João” da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        //Aumentar salário em 10%
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        }

        //Imprimir todos os funcionários com todas suas informações
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(" Lista de Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            String nome = funcionario.getNome();
            String dataNascimento = funcionario.getDataNascimento().format(dateFormatter);
            String salario = String.format(Locale.GERMANY, "%,.2f", funcionario.getSalario());
            String funcao = funcionario.getFuncao();

            System.out.println("Nome: " + nome + ", Data de Nascimento: " + dataNascimento + ", Salário: " + salario + ", Função: " + funcao);
        }

        //Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        //Imprimir os funcionários, agrupados por função
        System.out.println("\n - Funcionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> {
                String nome = funcionario.getNome();
                String dataNascimento = funcionario.getDataNascimento().format(dateFormatter);
                String salario = String.format(Locale.GERMANY, "%,.2f", funcionario.getSalario());
                System.out.println("\tNome: " + nome + ", Data de Nascimento: " + dataNascimento + ", Salário: " + salario);
            });
        });

        //Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("\n Funcionários que fazem aniversário nos meses 10 e 12:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> {
                    String nome = funcionario.getNome();
                    String dataNascimento = funcionario.getDataNascimento().format(dateFormatter);
                    String salario = String.format(Locale.GERMANY, "%,.2f", funcionario.getSalario());
                    String funcao = funcionario.getFuncao();
                    System.out.println("Nome: " + nome + ", Data de Nascimento: " + dataNascimento + ", Salário: " + salario + ", Função: " + funcao);
                });

        //Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade
        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (funcionarioMaisVelho != null) {
            int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\n Funcionário com a maior idade:");
            System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade);
        }

        //Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n Lista de Funcionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> {
                    String nome = funcionario.getNome();
                    String dataNascimento = funcionario.getDataNascimento().format(dateFormatter);
                    String salario = String.format(Locale.GERMANY, "%,.2f", funcionario.getSalario());
                    String funcao = funcionario.getFuncao();
                    System.out.println("Nome: " + nome + ", Data de Nascimento: " + dataNascimento + ", Salário: " + salario + ", Função: " + funcao);
                });

        //Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n Total dos salários dos funcionários: " + String.format(Locale.GERMANY, "%,.2f", totalSalarios));

        //Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n Quantidade de salários mínimos que cada funcionário ganha:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("Nome: " + funcionario.getNome() + ", Salários Mínimos: " + quantidadeSalariosMinimos);
        }
    }
}
