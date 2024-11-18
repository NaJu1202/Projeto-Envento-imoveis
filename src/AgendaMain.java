import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class AgendaMain {
    // variáveis globais
    static StringBuffer memoria = new StringBuffer();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        char opcao, resp = 'N';
        do {
            opcao = menu();
            switch (opcao) {
                case '1':
                    inserirDados();
                    break;
                case '2':
                    alterarDados();
                    break;
                case '3':
                    excluirDados();
                    break;
                case '4':
                    consultarDados();
                    break;
                case '5':
                    System.out.println("Deseja realmente sair do programa? S/N");
                    resp = Character.toUpperCase(scan.next().charAt(0));
                    break;
                default:
                    System.out.println("opção invalida, tente novamente.");
            }
        } while (resp != 'S');
        System.exit(0);
    }

    static char menu() {
        System.out.println("\n\nEscolha uma Opção:\n" +
                "1. Inserir novo contato\n" +
                "2. Alterar contato de uma pessoa\n" +
                "3. Excluir um contato\n" +
                "4. Pesquisar contato\n" +
                "5. Sair");
        return scan.next().charAt(0);
    }

    // este método server para atualizar a variável memoria com os dados que estão
    // no HD
    static void iniciarArquivo() {
        try {
            BufferedReader arquivoEntrada;
            arquivoEntrada = new BufferedReader(new FileReader("Agenda.txt"));
            String linha = "";
            memoria.delete(0, memoria.length());// apaga tudo que está na variável memoria
            do {
                linha = arquivoEntrada.readLine();
                if (linha != null) {
                    memoria.append(linha + "\n");
                }
            } while (linha != null);
            arquivoEntrada.close();
        } catch (FileNotFoundException erro) {
            System.out.println("\nArquivo não encontrado");
        } catch (Exception e) {
            System.out.println("\nErro de Leitura!");
        }
    }

    // este método grava os dados na memória segundária(HD, pendrive)
    public static void gravarDados() {
        try {
            BufferedWriter arquivoSaida;
            arquivoSaida = new BufferedWriter(new FileWriter("Agenda.txt"));
            arquivoSaida.write(memoria.toString());
            arquivoSaida.flush();
            arquivoSaida.close();
        } catch (Exception e) {
            System.out.println("\nErro de gravacao!");
        }
    }

    static void inserirDados() {
        int codigo;
        String nome, telefone;
        try {
            System.out.println("Digite o código da pessoa:");
            codigo = scan.nextInt();
            System.out.println("Digite o nome:");
            nome = scan.next();
            System.out.println("Digite o telefone:");
            telefone = scan.next();
            Agenda reg = new Agenda(codigo, nome, telefone);
            memoria.append(reg.toString()); // inserir uma nova linha no final
            gravarDados(); // grava alteração no HD
        } catch (Exception e) {
            System.out.println("\nErro de gravacao");
        }
    }

    public static void alterarDados() {
        String codigo, nome, telefone;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;
        iniciarArquivo(); // atualizar a variavel memoria para iniciar a pesquisa
        if (memoria.length() != 0) { // não está vazia
            System.out.println("\nDigite o codigo para alteração:");
            procura = scan.nextInt();
            inicio = 0; // inicio começa na posição 0
            while ((inicio != memoria.length()) && (!achou)) {
                ultimo = memoria.indexOf("\t", inicio);
                codigo = memoria.substring(inicio, ultimo);
                primeiro = ultimo + 1;
                ultimo = memoria.indexOf("\t", primeiro);
                nome = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;
                fim = memoria.indexOf("\n", primeiro);
                telefone = memoria.substring(primeiro, fim);
                Agenda reg = new Agenda(Integer.parseInt(codigo), nome, telefone);
                if (procura == reg.getCodigo()) {
                    System.out.println("\nCódigo: " + reg.getCodigo() +
                            " nome: " + reg.getNome() + " telefone: " + reg.getTel());
                    System.out.println("Entre com novo telefone:");
                    reg.setTel(scan.next());
                    memoria.replace(inicio, fim + 1, reg.toString()); // alterar dados na memoria
                    gravarDados(); // grava alteração no HD
                    achou = true;
                }
                inicio = fim + 1; // continua procurando o código da pessoa
            }
            if (achou) {
                System.out.println("\nalteração realizada com sucesso");
            } else {
                System.out.println("\ncódigo não encontrado");
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }

    public static void excluirDados() {
        String codigo, nome, telefone;
        int inicio, fim, ultimo, primeiro, procura;
        boolean achou = false;
        char resp;
        iniciarArquivo(); // atualizar a variavel memoria para iniciar a pesquisa
        if (memoria.length() != 0) { // não está vazia
            System.out.println("\nDigite o codigo para exclusão:");
            procura = scan.nextInt();
            inicio = 0; // inicio começa na posição 0 da variável memoria
            while ((inicio != memoria.length()) && (!achou)) {
                ultimo = memoria.indexOf("\t", inicio);
                codigo = memoria.substring(inicio, ultimo);
                primeiro = ultimo + 1;
                ultimo = memoria.indexOf("\t", primeiro);
                nome = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;
                fim = memoria.indexOf("\n", primeiro);
                telefone = memoria.substring(primeiro, fim);
                Agenda reg = new Agenda(Integer.parseInt(codigo), nome, telefone);
                if (procura == reg.getCodigo()) {
                    System.out.println("Deseja excluir?" + "\n" + "Digite S ou N" + "\n\n" +
                            "Código: " + reg.getCodigo() + " nome: " + reg.getNome() + " telefone: " + reg.getTel());
                    resp = Character.toUpperCase(scan.next().charAt(0));
                    if (resp == 'S') {
                        memoria.delete(inicio, fim + 1);
                        System.out.println("Registro excluido.");
                        gravarDados();
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
                    achou = true;
                }
                inicio = fim + 1; // continua procurando o código da pessoa
            }
            if (!achou) {
                System.out.println("\ncódigo não encontrado");
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }

    public static void consultarDados() {
        String codigo, nome, telefone;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;
        iniciarArquivo(); // atualizar a variavel memoria para iniciar a pesquisa
        if (memoria.length() != 0) { // não está vazia
            System.out.println("\nDigite o codigo para pesquisar:");
            procura = scan.nextInt();
            inicio = 0;
            while ((inicio != memoria.length()) && (!achou)) {
                ultimo = memoria.indexOf("\t", inicio);
                codigo = memoria.substring(inicio, ultimo);
                primeiro = ultimo + 1;
                ultimo = memoria.indexOf("\t", primeiro);
                nome = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;
                fim = memoria.indexOf("\n", primeiro);
                telefone = memoria.substring(primeiro, fim);
                Agenda reg = new Agenda(Integer.parseInt(codigo), nome, telefone);
                if (procura == reg.getCodigo()) {
                    System.out.println("\nCódigo: " + reg.getCodigo() +
                            " nome: " + reg.getNome() +
                            " telefone: " + reg.getTel());
                    achou = true;
                }
                inicio = fim + 1; // continua procurando o código da pessoa
            }
            if (!achou) {
                System.out.println("\ncódigo não encontrado");
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }
}
// fim do programa