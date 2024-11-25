import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EventoMain {
    static StringBuffer memoria = new StringBuffer();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        char opcao;
        Cliente cliente = new Cliente();
        Imoveis imoveis = new Imoveis();
        System.out.println("\nSeja bem vindo ao evento de Imóveis!\n");
        try {
            do {
                deletarMemoria();
                exibirMenu();
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
                        excluirDados();
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
            scan.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler opção");
        }
    }

    public static void deletarMemoria() {
        memoria.delete(0, memoria.length());
    }

    public static void exibirMenu() {
        System.out.println("\nEscolha a opção :)\n");
        System.out.println("1 - Inserir o imóvel\n" +
                "2 - Inserir o cliente\n" +
                "3 - Alterar cliente\n" +
                "4 - Excluir o cliente\n" +
                "5 - Listar os imóveis disponíveis\n" +
                "6 - Listar os clientes cadastrados\n" +
                "7 - Consultar o cliente específico\n" +
                "8 - Sair do programa\n");
    }

    public static void gravarArquivo(String arquivo, boolean append_mode) {
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
    static void inserirImoveis(Imoveis imoveis) {
        try {
            deletarMemoria();
            System.out.println("Insira o Código do imovel: ");
            int codigoImovel = scan.nextInt();

            iniciarArquivo("imoveis.txt");
            while (memoria.indexOf(String.valueOf(codigoImovel)) != -1) {
                System.out.println("Código do imóvel já existe ou menor que zero! Insira um código diferente:");
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

            scan.nextLine();
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
        } catch (NumberFormatException e) {
            System.out.println("\nERRO ao inserir imóvel - dados invalidos");
        } catch (Exception e) {
            System.out.println("\nERRO ao inserir imóvel - outro erro");
        }
    }
    static void inserirCliente(Cliente cliente) {
        try {
            deletarMemoria();

            System.out.println("Insira o ID:");
            cliente.setId(scan.nextInt());
            while (cliente.getId() < 0) {
                System.out.println("ID menor que zero! Insira um ID diferente:");
                cliente.setId(scan.nextInt());
            }

            scan.nextLine();
            System.out.println("Insira o nome:");
            cliente.setNome(scan.nextLine());

            System.out.println("Insira o telefone:");
            cliente.setTelefone(scan.next());

            System.out.println("\nInsira um código de imóvel: [QUE ESTEJA NA LISTA]\n");
            pesquisaGeralDeImoveis();
            System.out.println("\nDigite o código do imóvel:");
            int codigoImovel = scan.nextInt();

            iniciarArquivo("imoveis.txt");
            while (memoria.indexOf(String.valueOf(codigoImovel)) == -1) {
                System.out.println("\nCódigo do imóvel não encontrado. Insira um código válido:\n");
                pesquisaGeralDeImoveis();
                codigoImovel = scan.nextInt();
            }
            cliente.setCodigoImovel(codigoImovel);

            deletarMemoria();
            memoria.append(cliente.toString());
            gravarArquivo("clientes.txt", true);

        } catch (InputMismatchException e) {
            System.out.println("\nERRO ao inserir cliente - dados invalidos");
        } catch (NumberFormatException e) {
            System.out.println("\nERRO ao inserir imóvel - dados invalidos");
        } catch (Exception e) {
            System.out.println("\nERRO ao inserir cliente");
        }
    }
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
    public static void alterarDadosCliente() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;

        try {
            iniciarArquivo("clientes.txt");

            if (memoria.length() != 0) {
                System.out.println("\nDigite o codigo para alteração:");
                procura = scan.nextInt();
                inicio = 0;

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

                        memoria.replace(inicio, fim + 1, cliente_modificado.toString());
                        gravarArquivo("clientes.txt", false);
                        achou = true;
                    }
                    inicio = fim + 1;
                } while ((inicio != memoria.length()) && (!achou));

                if (achou) {
                    System.out.println("\nalteração realizada com sucesso");
                } else {
                    System.out.println("\ncódigo não encontrado");
                }
            } else {
                System.out.println("\narquivo vazio");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nERRO ao modificar cliente - dados invalidos");
        } catch (NumberFormatException e) {
            System.out.println("Erro na leitura de dados");
        } catch (Exception e) {
            System.out.println("Erro ao modificar dados");
        }
    }
    public static void consultarClienteEspecifico() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;
        try {
            iniciarArquivo("clientes.txt");

            if (memoria.length() != 0) {
                System.out.println("Digite o id para pesquisar:");
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

                        System.out.println("\nO(a) cliente(a): ");

                        System.out.println("Código: " + cliente_pesquisa.getId() +
                                " | Nome: " + cliente_pesquisa.getNome() + " | Telefone: "
                                + cliente_pesquisa.getTelefone()
                                + " | Código do imovel: " + cliente_pesquisa.getCodigoImovel() + "\n");

                        System.out.println("Está relacionado(a) com o imovel:");
                        ligarImovelAoCliente(cliente_pesquisa.getCodigoImovel());

                        achou = true;
                    }
                    inicio = fim + 1;
                }
                if (!achou) {
                    System.out.println("\ncódigo não encontrado");
                }
            } else {
                System.out.println("\narquivo vazio");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro no tipo de opção");
        } catch (NumberFormatException e) {
            System.out.println("\nERRO ao inserir imóvel - dados invalidos");
        } catch (NoSuchElementException e) {
            System.out.println("O usuario preferiu não digitar nada");
        } catch (Exception e) {
            System.out.println("Erro ao ler o dado inserido");
        }
    }
    public static void ligarImovelAoCliente(int codigoImovel) {
        String codigo, cidade, uf, tipoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;

        iniciarArquivo("imoveis.txt");

        if (memoria.length() != 0) {
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
                inicio = fim + 1;
            }
            if (!achou) {
                System.out.println("\ncódigo não encontrado");
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }
    public static void pesquisaGeralDeImoveis() {
        String codigo, cidade, uf, tipoImovel;
        int inicio, fim, ultimo, primeiro;

        iniciarArquivo("imoveis.txt");

        if (memoria.length() != 0) {
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

                inicio = fim + 1;
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }
    public static void pesquiaGeralDeClientes() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;

        iniciarArquivo("clientes.txt");

        if (memoria.length() != 0) {
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

                inicio = fim + 1;
            }
        } else {
            System.out.println("\narquivo vazio");
        }
    }
    public static void excluirDados() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro, procura;
        boolean achou = false;
        char resp;
        try {
            iniciarArquivo("clientes.txt");

            if (memoria.length() != 0) {
                System.out.println("\nDigite o codigo para exclusão:");
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

                    Cliente cliente = new Cliente(Integer.parseInt(id), nome, telefone, Integer.parseInt(codigoImovel));

                    if (procura == cliente.getId()) {

                        System.out.println("Deseja excluir?" + "\n" + "Digite S ou N" + "\n\n" +
                                "Código: " + cliente.getId() + " nome: " + cliente.getNome() + " telefone: "
                                + cliente.getTelefone());

                        resp = Character.toUpperCase(scan.next().charAt(0));

                        if (resp == 'S') {
                            memoria.delete(inicio, fim + 1);
                            System.out.println("Registro excluido.");
                            gravarArquivo("clientes.txt", false);
                        } else {
                            System.out.println("Exclusão cancelada.");
                        }
                        achou = true;
                    }
                    inicio = fim + 1;
                }
                if (!achou) {
                    System.out.println("\ncódigo não encontrado");
                }
            } else {
                System.out.println("\narquivo vazio");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro no tipo de opção");
        } catch (NumberFormatException e) {
            System.out.println("\nERRO ao excluir cliente - dados invalidos");
        } catch (Exception e) {
            System.out.println("Erro ao ler o dado inserido");
        }
    }
}