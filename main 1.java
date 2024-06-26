import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque();
        ArrayList<Cliente> clientes = new ArrayList<>();
        Cliente clienteLogado = null; // Inicialmente nenhum cliente está logado

        while (true) {
            if (clienteLogado == null) {
                clienteLogado = exibirMenuLogin(scanner, clientes, estoque); // Exibe o menu de login e retorna o cliente logado
            } else {
                boolean continuar = exibirMenuPrincipal(scanner, clienteLogado, estoque, clientes); // Exibe o menu principal e verifica se o cliente quer continuar
                if (!continuar) {
                    clienteLogado = null; // Reseta o cliente logado se ele decidir sair
                }
            }
        }
    }
    
    // Exibe o menu de login
    private static Cliente exibirMenuLogin(Scanner scanner, ArrayList<Cliente> clientes, Estoque estoque) {
        Cliente clienteLogado = null; // Inicialmente nenhum cliente está logado.2
    
        while (clienteLogado == null) {
            System.out.println("\nMenu de Login:\n");
            System.out.println("1. Adicionar cliente");
            System.out.println("2. Acessar cliente existente");
            System.out.println("3. Entrar sem cadastro");
            System.out.println("4. Sair");
    
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
    
            switch (opcao) {
                case 1:
                    Cliente novoCliente = cadastrarNovoCliente(scanner, clientes); // Permite que o usuário cadastre um novo cliente
                    if (novoCliente != null) {
                        clientes.add(novoCliente); // Adiciona o novo cliente à lista de clientes
                        System.out.println("Novo cliente cadastrado com sucesso!");
                        clienteLogado = novoCliente; // Loga "automaticamente" o novo cliente
                    }
                    break;
                case 2:
                    clienteLogado = acessarClienteExistente(scanner, clientes, estoque); // Permite que o usuário acesse um cliente existente
                    break;
                case 3:
                    System.out.println("Entrando sem cadastro...");
                    clienteLogado = new Cliente("Visitante", "");
                    break;
                case 4:
                    System.out.println("Saindo.");
                    System.exit(0); // Finaliza o programa
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
            }
        }
    
        return clienteLogado;
    }

    // Permite que o usuário acesse um cliente existente com nome e CPF
    private static Cliente acessarClienteExistente(Scanner scanner, ArrayList<Cliente> clientes, Estoque estoque) {
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine();

        Cliente cliente = buscarCliente(nomeCliente, cpfCliente, clientes); // Busca o cliente na lista de clientes

        if (cliente != null) {
            System.out.println("Cliente autenticado com sucesso!");
            return cliente; // Retorna o cliente encontrado
        } else {
            System.out.println("Cliente não encontrado. Verifique o nome e o CPF digitados."); // Cliente não encontrado
            return null;
        }
    }

    // Busca um cliente na lista de clientes pelo nome e CPF
    private static Cliente buscarCliente(String nomeCliente, String cpfCliente, ArrayList<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nomeCliente) && cliente.getCpf().equals(cpfCliente)) {
                return cliente; // Retorna o cliente encontrado
            }
        }
        return null; // Retorna null se o cliente não for encontrado
    }
    
    // Exibe o menu principal
    private static boolean exibirMenuPrincipal(Scanner scanner, Cliente clienteLogado, Estoque estoque, ArrayList<Cliente> clientes) {
        while (true) {
            System.out.println("\nMenu Principal:\n");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Remover produto");
            System.out.println("3. Consultar produto");
            System.out.println("4. Atualizar produto");
            System.out.println("5. Comprar produto");
            System.out.println("6. Ver carrinho");
            System.out.println("7. Ver dados do cliente");
            System.out.println("8. Ver saldo total do cliente");
            System.out.println("9. Ver todos os produtos em estoque");
            System.out.println("10. Encerrar compra");

            System.out.print("Escolha uma opção: ");
            int opcaoSecundaria = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcaoSecundaria) {
                case 1:
                    adicionarProduto(estoque, scanner); // Adicionar um novo produto ao estoque
                    break;
                case 2:
                    removerProduto(estoque, scanner); // Remover um produto do estoque
                    break;
                case 3:
                    consultarProduto(estoque, scanner); // Consultar um produto no estoque
                    break;
                case 4:
                    atualizarProduto(estoque, scanner); // Atualizar as informações de um produto no estoque
                    break;
                case 5:
                    comprarProduto(clienteLogado, estoque, scanner); // Comprar um produto do estoque
                    break;
                case 6:
                    verCarrinho(clienteLogado, scanner); // Mostrar o carrinho de compras do cliente
                    break;
                case 7:
                    verDadosCliente(clienteLogado); // Mostrar os dados do cliente
                    break;
                case 8:
                    verSaldoTotalCliente(clienteLogado, estoque); // Mostrar o saldo total do cliente
                    break;
                case 9:
                    verProdutosEmEstoque(estoque); // Mostrar todos os produtos no estoque (disponivel e indisponivel)
                    break;
                case 10:
                    System.out.print("Deseja continuar com outro usuário? ");
                    String continuarComOutroUsuario = String.valueOf(scanner.nextLine().trim().toLowerCase().charAt(0));

                    if (continuarComOutroUsuario.equals("s")) {
                        return false; // Retorna falso para voltar ao menu de login (1)
                    } else {
                        System.out.println("Saindo.");
                        System.exit(0); // Finaliza o programa
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Digite uma opção válida.");
            }
        }
    }

    // Permite que o usuario adicione um novo produto ao estoque
    private static void adicionarProduto(Estoque estoque, Scanner scanner) {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine(); // Le a linha toda, incluindo espaços em branco

        System.out.print("Digite a quantidade do produto: ");
        int quantidade = Integer.parseInt(scanner.nextLine().trim()); // Le a quantidade e remove espaços em branco

        System.out.print("Digite o preço do produto: ");
        double preco = Double.parseDouble(scanner.nextLine().trim()); // Le o preço e remove espaços em branco

        Produto novoProduto = new Produto(nome, quantidade, preco);
        estoque.adicionarProduto(novoProduto);
    }

    // Remove um produto do estoque
    private static void removerProduto(Estoque estoque, Scanner scanner) {
        System.out.print("Digite o nome do produto que deseja remover: ");
        String nomeProduto = scanner.nextLine();
        estoque.removerProduto(nomeProduto);
    }

    // Consulta um produto no estoque
    private static void consultarProduto(Estoque estoque, Scanner scanner) {
        System.out.print("Digite o nome do produto que deseja consultar: ");
        String nomeProduto = scanner.nextLine();
        estoque.consultarProduto(nomeProduto);
    }

    // Atualiza as informações de um produto no estoque
    private static void atualizarProduto(Estoque estoque, Scanner scanner) {
        System.out.print("Digite o nome do produto que deseja atualizar: ");
        String nomeProduto = scanner.nextLine();

        System.out.print("Digite a nova quantidade do produto: ");
        int novaQuantidade = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Digite o novo preço do produto: ");
        double novoPreco = scanner.nextDouble();
        scanner.nextLine(); 

        estoque.atualizarProduto(nomeProduto, novaQuantidade, novoPreco);
    }

    // Permite que o cliente compre um produto do estoque
    private static void comprarProduto(Cliente clienteLogado, Estoque estoque, Scanner scanner) {
        System.out.print("Digite o nome do produto que deseja comprar: ");
        String nomeProduto = scanner.nextLine();
        
        Produto produto = estoque.buscarProduto(nomeProduto);
        
        if (produto != null) {
            clienteLogado.comprarProduto(estoque, produto);
        } else {
            System.out.println("Produto não encontrado no estoque.");
        }
    }

    // Permite que um novo cliente seja cadastrado
    private static Cliente cadastrarNovoCliente(Scanner scanner, ArrayList<Cliente> clientes) {
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();
    
        System.out.print("Digite o CPF do cliente (11 dígitos): ");
        String cpfCliente = scanner.nextLine();
        while (!validarCPF(cpfCliente)) {
            System.out.println("CPF inválido. O CPF válido deve ter 11 dígitos.");
            System.out.print("Digite o CPF do cliente novamente: ");
            cpfCliente = scanner.nextLine();
        }
    
        // Cria um novo cliente
        Cliente novoCliente = new Cliente(nomeCliente, cpfCliente);
    
        // Verifica se o nome ou CPF já estão associados a outro cliente
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nomeCliente) || cliente.getCpf().equals(cpfCliente)) {
                System.out.println("Este nome ou CPF já está associado a outro cliente.");
                return null; // Retorna null se houver duplicatas
            }
        }
    
        return novoCliente;
    }

    // Mostra o carrinho de compras do cliente
    private static void verCarrinho(Cliente clienteLogado, Scanner scanner) {
        clienteLogado.mostrarCarrinho();
    }

    // Mostra os dados do cliente
    private static void verDadosCliente(Cliente clienteLogado) {
        if (clienteLogado != null && !clienteLogado.getNome().equalsIgnoreCase("visitante")) {
            System.out.println("Dados do cliente:");
            System.out.println("Nome: " + clienteLogado.getNome());
            System.out.println("CPF: " + clienteLogado.getCpf());
        } else {
            System.out.println("Cliente sem cadastro.");
        }
    }

    // Mostra o saldo total do cliente
    private static void verSaldoTotalCliente(Cliente clienteLogado, Estoque estoque) {
        double saldoTotal = 0;
        for (String item : clienteLogado.getCarrinho()) {
            String[] dadosItem = item.split(" \\(Quantidade: ");
            String nomeProduto = dadosItem[0].trim();
            int quantidade = Integer.parseInt(dadosItem[1].replaceAll("[^0-9]", ""));
            Produto produto = estoque.buscarProduto(nomeProduto);
            if (produto != null) {
                saldoTotal += quantidade * produto.getPreco();
            }
        }
        System.out.printf("Saldo total do cliente %s: R$%.2f%n", clienteLogado.getNome(), saldoTotal);
    }

    // Mostra todos os produtos no estoque
    private static void verProdutosEmEstoque(Estoque estoque) {
        if (estoque.getProdutos().isEmpty()) {
            System.out.println("Não há produtos no estoque.");
        } else {
            System.out.println("Produtos em estoque:");
            for (Produto produto : estoque.getProdutos()) {
                String status = (produto.getQuantidade() > 0) ? "Disponível" : "Esgotado"; // Verifica se o produto está disponível ou esgotado
                System.out.printf("%s: %d unidades (%s)%n", produto.getNome(), produto.getQuantidade(), status);
            }
        }
    }

    // Valida o formato do CPF
    private static boolean validarCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }
}
