import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EventoMain {
    static StringBuffer memoria = new StringBuffer(); // memoria do arquivo
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        char opcao;
        Cliente cliente = new Cliente();
        Imoveis imoveis = new Imoveis();
        System.out.println("\nSeja bem vindo ao evento de Imóveis!\n");

        do {
            deletarMemoria();
            System.out.println("\nEscolha a opção :)\n");
            System.out.println("1 - Inserir o imóvel\n" +
                    "2 - Inserir o cliente\n" +
                    "3 - Alterar cliente\n" +
                    "4 - Excluir o cliente\n" +
                    "5 - Listar os imóveis disponiveis\n" +
                    "6 - Listar os clientes cadastrados\n" +
                    "7 - Consultar o cliente específico\n" +
                    "8 - Sair do programa\n");
            opcao = scan.next().charAt(0);
            switch (opcao) {
                case '1':
                    inserirImoveis(imoveis);
                    break;
                case '2':
                    inserirCliente(cliente);
                    break;
                case '3':
                    alterarDadosCliente();
                    break;
                case '4':
                    break;
                case '5':
                    pesquisaGeralDeImoveis();
                    break;
                case '6':
                    pesquiaGeralDeClientes();
                    break;
                case '7':
                    consultarClienteEspecifico();
                    break;
                case '8':
                    System.out.println("<----- Encerrando o programa ----->");
                    System.out.println(
                            "   \nPrograma feito por:\n" + "\n\tAnna Julia Aleixo\n" + "\tAnderson Moreira\n"
                                    + "\tMayara Hafez\n");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != '8');
    }

    public static void deletarMemoria() {
        memoria.delete(0, memoria.length());
    }
    public static void gravarArquivo(String arquivo, boolean append_mode) { // append_mode = boolean, pq ao modificar
                                                                            // dados estava dando append
        try {
            BufferedWriter arquivoSaida;
            arquivoSaida = new BufferedWriter(new FileWriter(arquivo, append_mode));
            arquivoSaida.write(memoria.toString());
            arquivoSaida.flush();
            arquivoSaida.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("\nErro de gravacao!");
        }
    }

    // ----- INSERIR DADOS -----
    static void inserirImoveis(Imoveis imoveis) {
        try {
            deletarMemoria();
            System.out.println("Insira o Código do imovel: ");
            int codigoImovel = scan.nextInt();

            // Verificar se o código do imóvel já existe
            iniciarArquivo("imoveis.txt");
            while (memoria.indexOf(String.valueOf(codigoImovel)) != -1) {
                System.out.println("Código do imóvel já existe! Insira um código diferente:");
                codigoImovel = scan.nextInt();
            }
            imoveis.setCodigo(codigoImovel);

            if (imoveis.getCodigo() < 0) {
                System.out.println("Código inválido! O código não pode ser negativo.");
                while (imoveis.getCodigo() < 0) {
                    System.out.println("Insira um código válido:");
                    imoveis.setCodigo(scan.nextInt());
                }
            }

            scan.nextLine(); // para ler a cidade com espaços
            System.out.println("Insira a Cidade: ");
            imoveis.setCidade(scan.nextLine());
            System.out.println("Insira a UF:");
            imoveis.setUf(scan.next());
            System.out.println("Insira o Tipo do imóvel:");
            imoveis.setTipoImovel(scan.next());

            if (!imoveis.getTipoImovel().equalsIgnoreCase("Apartamento")
                    && !imoveis.getTipoImovel().equalsIgnoreCase("Casa")) {
                System.out.println("Tipo do imóvel inválido - apenas APARTAMENTO ou CASA");
                while (!imoveis.getTipoImovel().equalsIgnoreCase("Apartamento")
                        && !imoveis.getTipoImovel().equalsIgnoreCase("Casa")) {
                    System.out.println("Insira um tipo válido: [Apartamento/Casa]");
                    imoveis.setTipoImovel(scan.next());
                }
            }

            deletarMemoria();
            memoria.append(imoveis.toString());
            gravarArquivo("imoveis.txt", true);

        } catch (InputMismatchException e) {
            System.out.println("\nERRO ao inserir imóvel - dados invalidos");
        } catch (Exception e) {
            System.out.println("\nERRO ao inserir imóvel - outro erro");
        }
    }
    static void inserirCliente(Cliente cliente) {
        try {
            deletarMemoria();
            System.out.println("Insira o ID:");
            int idCliente = scan.nextInt();

            // Verificar se o ID do cliente já existe
            iniciarArquivo("clientes.txt");
            while ((memoria.indexOf(String.valueOf(idCliente)) != -1) || (idCliente < 0)) {
                System.out.println("ID já existente ou menor que zero! Insira um ID diferente:");
                idCliente = scan.nextInt();
            }
            cliente.setId(idCliente);

            scan.nextLine(); // para ler o nome com espaços
            System.out.println("Insira o nome:");
            cliente.setNome(scan.nextLine());

            System.out.println("Insira o telefone:");
            String telefone = scan.next();
            iniciarArquivo("clientes.txt");
            while (memoria.indexOf(telefone) != -1) { // Verificar se o telefone já está no arquivo
                System.out.println("Telefone já existente! Insira um telefone diferente:");
                telefone = scan.next();
            }
            cliente.setTelefone(telefone);

            System.out.println("Insira o código do imóvel: [QUE ESTEJA NA LISTA]");
            // Verificar se o código do imóvel informado existe
            pesquisaGeralDeImoveis();
            int codigoImovel = scan.nextInt();
            iniciarArquivo("imoveis.txt");
            while (memoria.indexOf(String.valueOf(codigoImovel)) == -1) {
                System.out.println("Código do imóvel não encontrado. Insira um código válido:");
                pesquisaGeralDeImoveis();
                codigoImovel = scan.nextInt();
            }
            cliente.setCodigoImovel(codigoImovel);

            deletarMemoria();
            memoria.append(cliente.toString());
            gravarArquivo("clientes.txt", true);

        } catch (InputMismatchException e) {
            System.out.println("\nERRO ao inserir cliente - dados invalidos");
        } catch (Exception e) {
            System.out.println("\nERRO ao inserir cliente");
        }
    }
    // ----- FIM DA INSERÇÃO DADOS -----

    // ----- INICIAR ARQUIVO -----
    static void iniciarArquivo(String arquivo) {
        try {
            deletarMemoria();
            BufferedReader arquivoEntrada;
            arquivoEntrada = new BufferedReader(new FileReader(arquivo));
            String linha = "";

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
    // ----- FIM DA INICIAR ARQUIVO -----

    // ----- ALTERAÇÕES DE DADOS -----
    public static void alterarDadosCliente() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;

        try {
            iniciarArquivo("clientes.txt"); // atualizar a variavel memoria para iniciar a pesquisa

            if (memoria.length() != 0) { // não está vazia
                System.out.println("\nDigite o codigo para alteração:");
                procura = scan.nextInt();
                inicio = 0; // inicio começa na posição 0

                do {
                    ultimo = memoria.indexOf("\t", inicio);
                    id = memoria.substring(inicio, ultimo);

                    primeiro = ultimo + 1;
                    ultimo = memoria.indexOf("\t", primeiro);
                    nome = memoria.substring(primeiro, ultimo);

                    primeiro = ultimo + 1;
                    ultimo = memoria.indexOf("\t", primeiro);
                    telefone = memoria.substring(primeiro, ultimo);

                    primeiro = ultimo + 1;
                    fim = memoria.indexOf("\n", primeiro);
                    codigoImovel = memoria.substring(primeiro, fim);

                    Cliente cliente_modificado = new Cliente(Integer.parseInt(id), nome, telefone,
                            Integer.parseInt(codigoImovel));

                    if (procura == cliente_modificado.getId()) {

                        System.out.println("\nCódigo: " + cliente_modificado.getId() +
                                " | Nome: " + cliente_modificado.getNome() + " | Telefone: "
                                + cliente_modificado.getTelefone()
                                + " | Código do imovel: " + cliente_modificado.getCodigoImovel());

                        System.out.println("Entre com novo telefone:");
                        cliente_modificado.setTelefone(scan.next());

                        memoria.replace(inicio, fim + 1, cliente_modificado.toString()); // alterar dados na memoria
                        gravarArquivo("clientes.txt", false);
                        achou = true;
                    }
                    inicio = fim + 1; // continua procurando o código da pessoa
                } while ((inicio != memoria.length()) && (!achou));

                if (achou) {
                    System.out.println("\nalteração realizada com sucesso");
                } else {
                    System.out.println("\ncódigo não encontrado");
                }
            } else {
                System.out.println("\narquivo vazio");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro na leitura de dados");
        } catch (Exception e) {
            System.out.println("Erro ao modificar dados");
        }
    }
    // ----- FIM DA ALTERAÇÃO DE DADOS -----

    // ----- CONSULTA DE DADOS -----
    public static void consultarClienteEspecifico() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;

        iniciarArquivo("clientes.txt"); // atualizar a variavel memoria para iniciar a pesquisa

        if (memoria.length() != 0) { // não está vazia
            System.out.println("\nDigite o id para pesquisar:");
            procura = scan.nextInt();
            inicio = 0;

            while ((inicio != memoria.length()) && (!achou)) {

                ultimo = memoria.indexOf("\t", inicio);
                id = memoria.substring(inicio, ultimo);
                primeiro = ultimo + 1;

                ultimo = memoria.indexOf("\t", primeiro);
                nome = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;

                ultimo = memoria.indexOf("\t", primeiro);
                telefone = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;

                fim = memoria.indexOf("\n", primeiro);
                codigoImovel = memoria.substring(primeiro, fim);

                Cliente cliente_pesquisa = new Cliente(Integer.parseInt(id), nome, telefone,
                        Integer.parseInt(codigoImovel));

                if (procura == cliente_pesquisa.getId()) {

                    System.out.println("\nCódigo: " + cliente_pesquisa.getId() +
                            " | Nome: " + cliente_pesquisa.getNome() + " | Telefone: "
                            + cliente_pesquisa.getTelefone()
                            + " | Código do imovel: " + cliente_pesquisa.getCodigoImovel());

                    ligarImovelAoCliente(cliente_pesquisa.getCodigoImovel());
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
    // ----- FIM DA CONSULTA DE DADOS -----

    // ----- LIGAR IMÓVEL AO CLIENTE -----
    public static void ligarImovelAoCliente(int codigoImovel) {
        String codigo, cidade, uf, tipoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;

        iniciarArquivo("imoveis.txt"); // atualizar a variavel memoria para iniciar a pesquisa

        if (memoria.length() != 0) { // não está vazia
            inicio = 0;

            while ((inicio != memoria.length()) && (!achou)) {

                ultimo = memoria.indexOf("\t", inicio);
                codigo = memoria.substring(inicio, ultimo);
                primeiro = ultimo + 1;

                ultimo = memoria.indexOf("\t", primeiro);
                cidade = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;

                ultimo = memoria.indexOf("\t", primeiro);
                uf = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;

                fim = memoria.indexOf("\n", primeiro);
                tipoImovel = memoria.substring(primeiro, fim);

                Imoveis imovel_pesquisa = new Imoveis(Integer.parseInt(codigo), cidade, uf,
                        tipoImovel);

                if (codigoImovel == imovel_pesquisa.getCodigo()) {

                    System.out.println("Código do imovel: " + imovel_pesquisa.getCodigo() +
                            " | Cidade: " + imovel_pesquisa.getCidade() + " | Uf: "
                            + imovel_pesquisa.getUf()
                            + " | Tipo do imovel: " + imovel_pesquisa.getTipoImovel() + "\n");
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

    // ----- FIM DA LIGAÇÃO DE DADOS -----

    // ----- PESQUISA DE DADOS -----
    public static void pesquisaGeralDeImoveis() {
        String codigo, cidade, uf, tipoImovel;
        int inicio, fim, ultimo, primeiro;

        iniciarArquivo("imoveis.txt"); // atualizar a variavel memoria para iniciar a pesquisa

        if (memoria.length() != 0) { // não está vazia
            inicio = 0;

            while ((inicio != memoria.length())) {

                ultimo = memoria.indexOf("\t", inicio);
                codigo = memoria.substring(inicio, ultimo);
                primeiro = ultimo + 1;

                ultimo = memoria.indexOf("\t", primeiro);
                cidade = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;

                ultimo = memoria.indexOf("\t", primeiro);
                uf = memoria.substring(primeiro, ultimo);
                primeiro = ultimo + 1;

                fim = memoria.indexOf("\n", primeiro);
                tipoImovel = memoria.substring(primeiro, fim);

                Imoveis imovel_pesquisa = new Imoveis(Integer.parseInt(codigo), cidade, uf,
                        tipoImovel);

                System.out.println("Código do imovel: " + imovel_pesquisa.getCodigo() +
                        " | Cidade: " + imovel_pesquisa.getCidade() + " | Uf: "
                        + imovel_pesquisa.getUf()
                        + " | Tipo do imovel: " + imovel_pesquisa.getTipoImovel() + "\n");

                inicio = fim + 1; // continua procurando o código da pessoa
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }

    public static void pesquiaGeralDeClientes() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;

        iniciarArquivo("clientes.txt"); // atualizar a variavel memoria para iniciar a pesquisa

        if (memoria.length() != 0) { // não está vazia
            inicio = 0;

            while ((inicio != memoria.length())) {

                ultimo = memoria.indexOf("\t", inicio);
                id = memoria.substring(inicio, ultimo);

                primeiro = ultimo + 1;
                ultimo = memoria.indexOf("\t", primeiro);
                nome = memoria.substring(primeiro, ultimo);

                primeiro = ultimo + 1;
                ultimo = memoria.indexOf("\t", primeiro);
                telefone = memoria.substring(primeiro, ultimo);

                primeiro = ultimo + 1;
                fim = memoria.indexOf("\n", primeiro);
                codigoImovel = memoria.substring(primeiro, fim);

                Cliente cliente_pesquisa = new Cliente(Integer.parseInt(id), nome, telefone,
                        Integer.parseInt(codigoImovel));

                System.out.println("\nCódigo: " + cliente_pesquisa.getId() +
                        " | Nome: " + cliente_pesquisa.getNome() + " | Telefone: "
                        + cliente_pesquisa.getTelefone()
                        + "| Código do imovel: " + cliente_pesquisa.getCodigoImovel());

                inicio = fim + 1; // continua procurando o código da pessoa
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }
    // ----- FIM DAS PESQUISAS DE DADOS -----
}