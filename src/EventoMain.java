import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class EventoMain {
    static StringBuffer memoria = new StringBuffer();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        char opcao;
        Cliente cliente = new Cliente();
        Imoveis imoveis = new Imoveis();
        System.out.println("Seja bem vindo ao evento de Imóveis!\n Digite a opção que desejar:");
        System.out.println("1 - Inserir o imóvel\n" +
                "2 - Inserir o cliente\n" +
                "3 - Alterar cliente\n" +
                "4 - Excluir o cliente\n" +
                "5 - Consultar o cliente\n" +
                "6 - Consultar o imóvel\n" +
                "7 - Sair do programa");
        opcao = scan.next().charAt(0);
        switch (opcao) {
            case '1':
                inserirImoveis(imoveis);
                break;
            case '2':
                inserirCliente(cliente);
                break;
            case '3':
                break;
            case '4':
                break;
            case '5':
                break;
            case '6':
                break;
            case '7':
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }

    // este método grava os dados na memória segundária(HD, pendrive)
    public static void gravarClientes() {
        try {
            BufferedWriter arquivoSaida;
            arquivoSaida = new BufferedWriter(new FileWriter("ClientesEvento.txt"));
            arquivoSaida.write(memoria.toString());
            arquivoSaida.flush();
            arquivoSaida.close();
        } catch (Exception e) {
            System.out.println("\nErro de gravacao!");
        }
    }

    public static void gravarImoveis() {
        try {
            BufferedWriter arquivoSaida;
            arquivoSaida = new BufferedWriter(new FileWriter("ImoveisEvento.txt"));
            arquivoSaida.write(memoria.toString());
            arquivoSaida.flush();
            arquivoSaida.close();
        } catch (Exception e) {
            System.out.println("\nErro de gravacao!");
        }
    }

    static void inserirCliente(Cliente cliente) {
        try {
            System.out.println("Insira o ID");
            cliente.setId(scan.nextInt());
            System.out.println("Insira o nome");
            cliente.setNome(scan.next());
            System.out.println("Insira o telefone");
            cliente.setTelefone(scan.nextInt());
            System.out.println("Insira o codigo do imovel");
            cliente.setCodigoImovel(scan.nextInt());

            memoria.append(cliente.toString()); // inserir uma nova linha no final
            gravarClientes(); // grava alteração no HD
        } catch (Exception e) {
            System.out.println("\nERRO de inserção");
        }
    }

    static void inserirImoveis(Imoveis imoveis) {
        try {
            System.out.println("Insira o Código do imovel: ");
            imoveis.setCodigo(scan.nextInt());

            System.out.println("Insira a Cidade: ");
            imoveis.setCidade(scan.nextLine());

            System.out.println("Insira a UF");
            imoveis.setUf(scan.next());

            System.out.println("Insira o Tipo do imóvel");
            imoveis.setTipoImovel(scan.next());

            memoria.append(imoveis.toString()); // inserir uma nova linha no final
            gravarImoveis(); // grava alteração no HD
        } catch (Exception e) {
            System.out.println("\nERRO de inserção");
        }
    }
}
