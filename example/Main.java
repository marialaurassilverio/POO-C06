package org.example;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    static Scanner teclado = new Scanner(System.in); // teclado usado em todo o programa
    static ArrayList<TipoCliente> clientes = new ArrayList<>(); // lista de clientes cadastrados

    // estoque da loja, usado tanto no menu de estoque quanto na hora de registrar uma compra
    static Estoque[] estoques = {
            new Estoque("Regata da Copa - P", 10),
            new Estoque("Regata da Copa - M", 10),
            new Estoque("Regata da Copa - G", 10),
            new Estoque("Regata da Copa - GG", 10),
            new Estoque("Camiseta da Copa (Brasil) - P", 10),
            new Estoque("Camiseta da Copa (Brasil) - M", 10),
            new Estoque("Camiseta da Copa (Brasil) - G", 10),
            new Estoque("Camiseta da Copa (Brasil) - GG", 10)
    };

    public static void main(String[] args) {

        int opcao = -1;

        while (opcao != 0) { // repete o menu principal até o usuário digitar 0

            System.out.println("\n############################################");
            System.out.println("#         LOJA DA COPA 2026                #");
            System.out.println("############################################");
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1 - Cliente (cadastro / busca)");
            System.out.println("2 - Registrar Compra");
            System.out.println("3 - Estoque");
            System.out.println("4 - Itens da loja");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = teclado.nextInt();

            switch (opcao) {
                case 1:
                    menuCliente();
                    break;
                case 2:
                    registrarCompra();
                    break;
                case 3:
                    menuEstoque();
                    break;
                case 4:
                    verItensDaLoja();
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        teclado.close();
    }

    // carrega os produtos da loja no catálogo do carrinho sem imprimir
    // a mensagem "Produto cadastrado: ..." toda vez que o programa inicia esse carrinho
    static void carregarProdutos(Carrinho carrinho) {
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            @Override public void write(int b) { }
        }));
        try {
            carrinho.cadastrarNovoProduto(new Produto(1, 59.90, "Regata da Copa - P"));
            carrinho.cadastrarNovoProduto(new Produto(2, 69.90, "Regata da Copa - M"));
            carrinho.cadastrarNovoProduto(new Produto(3, 79.90, "Regata da Copa - G"));
            carrinho.cadastrarNovoProduto(new Produto(4, 89.90, "Regata da Copa - GG"));
            carrinho.cadastrarNovoProduto(new Produto(5, 69.90, "Camiseta da Copa (Brasil) - P"));
            carrinho.cadastrarNovoProduto(new Produto(6, 79.90, "Camiseta da Copa (Brasil) - M"));
            carrinho.cadastrarNovoProduto(new Produto(7, 89.90, "Camiseta da Copa (Brasil) - G"));
            carrinho.cadastrarNovoProduto(new Produto(8, 99.90, "Camiseta da Copa (Brasil) - GG"));
        } finally {
            System.setOut(original);
        }
    }

    // descobre o nome do produto do estoque a partir do id usado no carrinho
    static String nomeProdutoPorId(int id) {
        switch (id) {
            case 1: return "Regata da Copa - P";
            case 2: return "Regata da Copa - M";
            case 3: return "Regata da Copa - G";
            case 4: return "Regata da Copa - GG";
            case 5: return "Camiseta da Copa (Brasil) - P";
            case 6: return "Camiseta da Copa (Brasil) - M";
            case 7: return "Camiseta da Copa (Brasil) - G";
            case 8: return "Camiseta da Copa (Brasil) - GG";
            default: return null;
        }
    }

    // procura no estoque o item com o nome informado
    static Estoque buscarEstoquePorNome(String nomeProduto) {
        for (Estoque e : estoques) {
            if (e.getTipoProduto().equals(nomeProduto)) {
                return e;
            }
        }
        return null;
    }

    // confere se tem estoque suficiente para todos os itens pedidos.
    // se faltar algum item, nao desconta nada e bloqueia a compra.
    // se tiver estoque para tudo, desconta de fato e libera a compra.
    static boolean validarEAbaterEstoque(java.util.Map<Integer, Integer> pedidoPorId) {

        if (pedidoPorId.isEmpty()) {
            return true; // carrinho vazio, nada a validar
        }

        for (java.util.Map.Entry<Integer, Integer> pedido : pedidoPorId.entrySet()) {
            String nomeProduto = nomeProdutoPorId(pedido.getKey());
            int quantidadePedida = pedido.getValue();

            Estoque estoqueDoItem = (nomeProduto != null) ? buscarEstoquePorNome(nomeProduto) : null;

            if (estoqueDoItem == null) {
                System.out.println("\nProduto sem controle de estoque cadastrado (ID " + pedido.getKey() + ").");
                return false;
            }
            if (!estoqueDoItem.disponivel(quantidadePedida)) {
                return false;
            }
        }

        for (java.util.Map.Entry<Integer, Integer> pedido : pedidoPorId.entrySet()) {
            String nomeProduto = nomeProdutoPorId(pedido.getKey());
            Estoque estoqueDoItem = buscarEstoquePorNome(nomeProduto);
            estoqueDoItem.removeProduto(pedido.getValue());
        }

        return true;
    }

    // menu de cliente: cadastrar, buscar e listar (sem registrar compra aqui)
    static void menuCliente() {

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n========== MENU CLIENTE ==========");
            System.out.println("1 - Cadastrar novo cliente");
            System.out.println("2 - Buscar cliente (CPF)");
            System.out.println("3 - Listar todos os clientes");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha: ");
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1:
                    TipoCliente novo = cadastrarNovoCliente();
                    clientes.add(novo);
                    System.out.println("Cliente cadastrado com sucesso!");
                    novo.mostraInfo();
                    break;
                case 2:
                    TipoCliente encontrado = buscarCliente();
                    if (encontrado == null) {
                        System.out.println("Cliente não encontrado.");
                    } else {
                        encontrado.mostraInfo();
                    }
                    break;
                case 3:
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado ainda.");
                    } else {
                        System.out.println("\n--- CLIENTES CADASTRADOS ---");
                        for (TipoCliente c : clientes) {
                            c.mostraInfo();
                        }
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // pede os dados do cliente pelo teclado e cria o objeto
    static TipoCliente cadastrarNovoCliente() {
        System.out.println("========== CADASTRO DE CLIENTE ==========");

        System.out.println("Digite o nome do cliente: ");
        String nome = teclado.nextLine();

        System.out.println("Digite o CPF do cliente: ");
        String cpf = teclado.nextLine();

        System.out.println("Digite o telefone do cliente: ");
        String telefone = teclado.nextLine();

        System.out.println("Digite o email do cliente: ");
        String email = teclado.nextLine();

        System.out.println("Digite o endereço do cliente: ");
        String endereco = teclado.nextLine();

        return new TipoCliente(nome, cpf, telefone, email, endereco);
    }

    // procura um cliente ja cadastrado pelo CPF
    static TipoCliente buscarCliente() {
        System.out.println("Digite o CPF do cliente: ");
        String busca = teclado.nextLine();

        for (TipoCliente c : clientes) {
            if (c.getCpf().equals(busca)) {
                System.out.println("Cliente encontrado: " + c.getNome());
                return c;
            }
        }
        return null;
    }

    // pergunta se o cliente é novo ou já cadastrado e devolve o cliente identificado.
    // retorna null se não conseguir identificar e o usuário não quiser se cadastrar.
    static TipoCliente identificarOuCadastrarCliente() {

        System.out.println("\n1 - Cliente novo");
        System.out.println("2 - Cliente já cadastrado");
        System.out.print("Escolha: ");
        int tipo = teclado.nextInt();
        teclado.nextLine();

        TipoCliente cliente;

        if (tipo == 1) {
            cliente = cadastrarNovoCliente();
            clientes.add(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            cliente = buscarCliente();

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                System.out.println("Deseja cadastrá-lo agora? (1 - Sim / 2 - Não): ");
                int resposta = teclado.nextInt();
                teclado.nextLine();

                if (resposta == 1) {
                    cliente = cadastrarNovoCliente();
                    clientes.add(cliente);
                    System.out.println("Cliente cadastrado com sucesso!");
                } else {
                    System.out.println("Não cadastrado.");
                    return null;
                }
            }
        }
        return cliente;
    }

    // registra uma compra: identifica o cliente, abre o carrinho dele,
    // confere o estoque, fecha a compra numerada e leva direto pro pagamento
    static void registrarCompra() {

        System.out.println("\n========== REGISTRAR COMPRA ==========");
        TipoCliente cliente = identificarOuCadastrarCliente();

        if (cliente == null) {
            System.out.println("Compra não registrada.");
            return;
        }

        Carrinho carrinho = new Carrinho();
        carregarProdutos(carrinho);

        // guarda em paralelo o que foi pedido (id -> quantidade), pois o
        // Carrinho não tem um jeito de listar os itens pra fora da classe
        java.util.Map<Integer, Integer> pedidoPorId = new java.util.HashMap<>();

        int escolha = -1;

        while (escolha != 5) {

            System.out.println("\n|======== CARRINHO DE " + cliente.getNome() + " ========|");
            System.out.println("|1 -> Ver produtos disponiveis");
            System.out.println("|2 -> Adicionar produto ao carrinho");
            System.out.println("|3 -> Remover produto do carrinho");
            System.out.println("|4 -> Ver carrinho");
            System.out.println("|5 -> Finalizar compra");
            System.out.println("|Escolhe uma opção");

            escolha = teclado.nextInt();

            switch (escolha) {
                case 1:
                    carrinho.listrCatalogo();
                    break;

                case 2:
                    System.out.print("Digite o ID do produto: ");
                    int idAdicionar = teclado.nextInt();
                    System.out.print("Digite a quantidade: ");
                    int quantidade = teclado.nextInt();
                    carrinho.adicionarAoCarrinho(idAdicionar, quantidade);
                    pedidoPorId.merge(idAdicionar, quantidade, Integer::sum);
                    break;

                case 3:
                    System.out.print("Digite o ID do produto para remover: ");
                    int idRemover = teclado.nextInt();
                    carrinho.removerProduto(idRemover);
                    pedidoPorId.remove(idRemover);
                    break;

                case 4:
                    carrinho.listarItensCarrinho();
                    break;

                case 5:
                    System.out.println("Finalizando compra...");
                    break;

                default:
                    System.out.println("Opção inválida! Digite um número entre 1 e 5.");
                    break;
            }
        }

        if (!validarEAbaterEstoque(pedidoPorId)) {
            System.out.println("\nCompra NÃO finalizada por falta de estoque. Ajuste o carrinho e tente novamente.");
            return;
        }

        Compra compra = new Compra(cliente, carrinho);
        cliente.registrarCompra(); // soma essa compra na contagem do cliente (nível comum/premium)
        compra.mostraResumo();
        System.out.println("Compra registrada com sucesso!");

        double totalCompra = compra.getTotal();

        if (totalCompra <= 0) {
            System.out.println("\nCarrinho vazio — nada a pagar.");
            return;
        }

        System.out.println("\nVamos prosseguir para o pagamento desta compra.");

        // desconto de acordo com o nível do cliente (comum ou premium)
        String tipoClienteDesconto = (cliente.getNivel() == TipoCliente.PREMIUM) ? "premium" : "comum";
        Desconto desconto = new Desconto(tipoClienteDesconto);
        double totalComDesconto = desconto.calculaDesconto(totalCompra);

        System.out.println("\n----- DESCONTO APLICADO -----");
        System.out.println(desconto);
        System.out.printf("Total do carrinho : R$ %.2f%n", totalCompra);
        System.out.printf("Total com desconto: R$ %.2f%n", totalComDesconto);
        System.out.println("------------------------------");

        Pagamento pagamento = new Pagamento(totalComDesconto); // valor já vem do carrinho, sem digitar nada

        int opPag = -1;

        while (opPag != 0) {

            System.out.println("\n--- MENU DE PAGAMENTO ---");
            System.out.println("1 - Pagar via Pix");
            System.out.println("2 - Pagar via Crédito");
            System.out.println("3 - Pagar via Débito");
            System.out.println("4 - Cancelar pagamento");
            System.out.println("5 - Emitir comprovante (console + .txt)");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha: ");
            opPag = teclado.nextInt();

            switch (opPag) {
                case 1:
                    pagamento.pix(teclado);
                    opPag = 0;
                    break;
                case 2:
                    pagamento.credito(teclado);
                    opPag = 0;
                    break;
                case 3:
                    pagamento.debito(teclado);
                    opPag = 0;
                    break;
                case 4:
                    pagamento.cancelar();
                    opPag = 0;
                    break;
                case 5:
                    System.out.print("Deseja emitir o comprovante?(1-SIM/2-NÃO: ");
                    int opcaoComp = teclado.nextInt();
                    if (opcaoComp == 1) {
                        pagamento.emitirComprovante();
                        opPag = 0;
                    } else {
                        System.out.println("Comprovante não emitido.");
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // menu de estoque: só consultar e gerenciar quantidade, sem pagamento
    static void menuEstoque() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n--- ESTOQUE ---");
            for (int i = 0; i < estoques.length; i++) {
                System.out.printf("%d - %-40s (Estoque: %d)%n",
                        i + 1,
                        estoques[i].getTipoProduto(),
                        estoques[i].getQtdProdutos());
            }
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha o produto (número): ");
            opcao = teclado.nextInt();

            if (opcao == 0) {
                System.out.println("Voltando ao menu principal...");
                break;
            }

            int idxProd = opcao - 1;

            if (idxProd < 0 || idxProd >= estoques.length) {
                System.out.println("Produto inválido!");
                continue;
            }

            Estoque estoque = estoques[idxProd];

            int opEst = -1;

            while (opEst != 0) {

                System.out.println("\n--- MENU DE ESTOQUE ---");
                System.out.println("Produto : " + estoque.getTipoProduto());
                System.out.println("Qtd atual: " + estoque.getQtdProdutos());
                System.out.println("1 - Adicionar produtos");
                System.out.println("2 - Remover produtos");
                System.out.println("3 - Verificar disponibilidade");
                System.out.println("0 - Voltar");
                System.out.print("Escolha: ");
                opEst = teclado.nextInt();

                switch (opEst) {
                    case 1:
                        System.out.print("Quantos produtos deseja adicionar? ");
                        estoque.addProduto(teclado.nextInt());
                        break;
                    case 2:
                        System.out.print("Quantos produtos deseja remover? ");
                        estoque.removeProduto(teclado.nextInt());
                        break;
                    case 3:
                        System.out.print("Quantos produtos deseja verificar? ");
                        estoque.disponivel(teclado.nextInt());
                        break;
                    case 0:
                        System.out.println("Voltando ao menu anterior...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }

            opcao = -1; // volta a mostrar a lista de estoque
        }
    }

    // mostra o catálogo de produtos da loja
    static void verItensDaLoja() {

        Carrinho catalogo = new Carrinho();
        carregarProdutos(catalogo);

        System.out.println("\n========== ITENS DA LOJA ==========");
        catalogo.listrCatalogo();
        System.out.println("====================================");
    }
}
