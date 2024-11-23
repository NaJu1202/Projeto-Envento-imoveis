import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class EventoMain {
    static StringBuffer memoria = new StringBuffer();
    static StringBuffer memoria2 = new StringBuffer();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        char opcao;
        Cliente cliente = new Cliente();
        Imoveis imoveis = new Imoveis();
        System.out.println("\nSeja bem vindo ao evento de Imóveis!\n");

        do {
            System.out.println("Escolha a opção :)\n");
            System.out.println("1 - Inserir o imóvel\n" +
                    "2 - Inserir o cliente\n" +
                    "3 - Alterar cliente\n" +
                    "4 - Excluir o cliente\n" +
                    "5 - Consultar o cliente\n" +
                    "6 - Consultar o imóvel\n" +
                    "7 - Sair do programa\n");
            opcao = scan.next().charAt(0);
            switch (opcao) {
                case '1':
                    inserirImoveis(imoveis);
                    break;
                case '2':
                    inserirCliente(cliente);
                    break;
                case '3':
                    alterarDados();
                    break;
                case '4':
                    break;
                case '5':
                    break;
                case '6':
                    break;
                case '7':
                    System.out.println("<----- Encerrando o programa ----->");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != '7');
    }

    // este método grava os dados na memória segundária(HD, pendrive)
    public static void gravarArquivo(String arquivo, boolean append_mode) {
        try {
            BufferedWriter arquivoSaida;
            arquivoSaida = new BufferedWriter(new FileWriter(arquivo, append_mode));
            arquivoSaida.write(memoria.toString());
            arquivoSaida.flush();
            arquivoSaida.close();
        } catch (Exception e) {
            System.out.println("\nErro de gravacao!");
        }
    }

    static void inserirImoveis(Imoveis imoveis) {
        try {
            System.out.println("Insira o Código do imovel: ");
            imoveis.setCodigo(scan.nextInt());
            System.out.println("Insira a Cidade: ");
            imoveis.setCidade(scan.next());
            System.out.println("Insira a UF");
            imoveis.setUf(scan.next());
            System.out.println("Insira o Tipo do imóvel");
            imoveis.setTipoImovel(scan.next());

            memoria.append(imoveis.toString()); // inserir uma nova linha no final
            gravarArquivo("imoveis.txt", true); // grava alteração no HD
            memoria.delete(0, memoria.length());

        } catch (Exception e) {
            System.out.println("\nERRO ao inserir imóvel");
        }
    }

    static void inserirCliente(Cliente cliente) {
        try {
            System.out.println("Insira o ID");
            cliente.setId(scan.nextInt());
            System.out.println("Insira o nome");
            cliente.setNome(scan.next());
            System.out.println("Insira o telefone");
            cliente.setTelefone(scan.next());
            System.out.println("Insira o codigo do imovel");
            cliente.setCodigoImovel(scan.nextInt());

            memoria.append(cliente.toString()); // inserir uma nova linha no final
            gravarArquivo("clientes.txt", true); // grava alteração no HD
            memoria.delete(0, memoria.length());

        } catch (Exception e) {
            System.out.println("\nERRO ao inserir cliente");
        }
    }

    // este método server para atualizar a variável memoria com os dados que estão
    // no HD
    static void iniciarArquivo() {
        try {
            BufferedReader arquivoEntrada;
            arquivoEntrada = new BufferedReader(new FileReader("clientes.txt"));
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

    public static void alterarDados() {
        String id, nome, telefone, codigoImovel;
        int inicio, fim, ultimo, primeiro;
        boolean achou = false;
        int procura;

        try {
            iniciarArquivo(); // atualizar a variavel memoria para iniciar a pesquisa

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
                                "| Nome: " + cliente_modificado.getNome() + "| Telefone: "
                                + cliente_modificado.getTelefone()
                                + "| Código do imovel: " + cliente_modificado.getCodigoImovel());

                        System.out.println("Entre com novo telefone:");
                        cliente_modificado.setTelefone(scan.next());

                        memoria.replace(inicio, fim + 1, cliente_modificado.toString()); // alterar dados na memoria
                        gravarArquivo("clientes.txt", false); // grava alteração no HD
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
}