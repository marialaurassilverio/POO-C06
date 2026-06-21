package org.example;

import java.util.Scanner;      // permite ler dados digitados pelo usuário no teclado
import java.util.ArrayList;    // estrutura de lista para guardar os clientes cadastrados

public class Main {

    // ───── recursos compartilhados entre os fluxos ─────
    static Scanner teclado = new Scanner(System.in); // único Scanner do programa, usado por todos os fluxos
    static ArrayList<TipoCliente> clientes = new ArrayList<>(); // lista de clientes cadastrados em memória

    // estoque único, compartilhado entre o fluxo de Estoque e o de Registrar Compra
    static Estoque[] estoques = {
            new Estoque("Regata da Copa - P", 10),              // estoque inicial: 10 unidades
            new Estoque("Regata da Copa - M", 10),
            new Estoque("Regata da Copa - G", 10),
            new Estoque("Regata da Copa - GG", 10),
            new Estoque("Camiseta da Copa (Brasil) - P", 10),
            new Estoque("Camiseta da Copa (Brasil) - M", 10),
            new Estoque("Camiseta da Copa (Brasil) - G", 10),
            new Estoque("Camiseta da Copa (Brasil) - GG", 10)
    };

    public static void main(String[] args) { // ponto de entrada do programa

        int opcao = -1; // -1 só para garantir que o while abaixo execute pelo menos uma vez

        while (opcao != 0) { // repete o menu principal até o usuário digitar 0 (Sair)

            System.out.println("\n############################################"); // imprime cabeçalho da loja
            System.out.println("#         LOJA DA COPA 2026                #");
            System.out.println("############################################");
            System.out.println("\n========= MENU PRINCIPAL =========");          // imprime o menu principal
            System.out.println("1 - Cliente (cadastro / busca)");
            System.out.println("2 - Registrar Compra");
            System.out.println("3 - Estoque");
            System.out.println("4 - Itens da loja");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = teclado.nextInt(); // lê a opção escolhida pelo usuário

            switch (opcao) { // direciona para o fluxo correspondente à opção escolhida
                case 1:
                    fluxoCliente(); // abre o menu de cliente (cadastrar/buscar/listar)
                    break;
                case 2:
                    fluxoRegistrarCompra(); // abre o fluxo completo de registrar uma compra
                    break;
                case 3:
                    fluxoEstoque(); // abre o menu de consulta/gestão de estoque
                    break;
                case 4:
                    fluxoItensDaLoja(); // mostra o catálogo de produtos da loja
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema. Até logo!"); // mensagem de despedida
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente."); // opção fora do menu
            }
        }

        teclado.close(); // fecha o Scanner ao sair do programa, liberando o recurso do teclado
    }

    // Carrega produtos de teste no catálogo do carrinho sem exibir a mensagem "Produto cadastrado: ..." no console (silencia temporariamente o System.out só durante essa carga inicial,sem precisar alterar a classe Carrinho).
    static void carregarProdutosSemImprimir(Carrinho carrinho) {
        java.io.PrintStream original = System.out; // guarda a referência da saída padrão original
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() { // troca a saída padrão por uma "muda"
            @Override public void write(int b) { /* descarta a saída, não imprime nada */ }
        }));
        try {
            // cadastra cada produto da loja no catálogo do carrinho (id, preço, nome)
            carrinho.cadastrarNovoProduto(new Produto(1, 59.90, "Regata da Copa - P"));
            carrinho.cadastrarNovoProduto(new Produto(2, 69.90, "Regata da Copa - M"));
            carrinho.cadastrarNovoProduto(new Produto(3, 79.90, "Regata da Copa - G"));
            carrinho.cadastrarNovoProduto(new Produto(4, 89.90, "Regata da Copa - GG"));
            carrinho.cadastrarNovoProduto(new Produto(5, 69.90, "Camiseta da Copa (Brasil) - P"));
            carrinho.cadastrarNovoProduto(new Produto(6, 79.90, "Camiseta da Copa (Brasil) - M"));
            carrinho.cadastrarNovoProduto(new Produto(7, 89.90, "Camiseta da Copa (Brasil) - G"));
            carrinho.cadastrarNovoProduto(new Produto(8, 99.90, "Camiseta da Copa (Brasil) - GG"));
        } finally {
            System.setOut(original); // restaura a saída padrão original, mesmo se algo der erro acima
        }
    }

    // nome do produto correspondente a cada ID do carrinho (precisa bater exatamente com o nome usado no array estoques[] para conseguir casar os dois)
    static String nomeProdutoPorId(int id) {
        switch (id) { // converte o id do produto no carrinho para o nome usado no estoque
            case 1: return "Regata da Copa - P";
            case 2: return "Regata da Copa - M";
            case 3: return "Regata da Copa - G";
            case 4: return "Regata da Copa - GG";
            case 5: return "Camiseta da Copa (Brasil) - P";
            case 6: return "Camiseta da Copa (Brasil) - M";
            case 7: return "Camiseta da Copa (Brasil) - G";
            case 8: return "Camiseta da Copa (Brasil) - GG";
            default: return null; // id desconhecido, não tem produto correspondente
        }
    }

    // procura, no array de estoque compartilhado, o item com o nome informado
    static Estoque buscarEstoquePorNome(String nomeProduto) {
        for (Estoque e : estoques) { // percorre todos os itens do estoque
            if (e.getTipoProduto().equals(nomeProduto)) { // compara o nome do item com o nome buscado
                return e; // encontrou, retorna o item de estoque correspondente
            }
        }
        return null; // não encontrou nenhum item de estoque com esse nome
    }

    // Valida se há estoque suficiente para TODOS os itens do pedido.
    // Se faltar qualquer item, não abate nada e retorna false (compra bloqueada). Se houver estoque para tudo, abate de fato e retorna true.
    static boolean validarEAbaterEstoque(java.util.Map<Integer, Integer> pedidoPorId) {

        if (pedidoPorId.isEmpty()) { // se o carrinho está vazio, não há nada para validar
            return true; // carrinho vazio, nada a validar
        }

        // 1ª passada: só verifica, sem alterar nada ainda
        for (java.util.Map.Entry<Integer, Integer> pedido : pedidoPorId.entrySet()) { // percorre cada item pedido
            String nomeProduto = nomeProdutoPorId(pedido.getKey()); // descobre o nome do produto pelo id
            int quantidadePedida = pedido.getValue(); // quantidade que o cliente quer comprar desse item

            Estoque estoqueDoItem = (nomeProduto != null) ? buscarEstoquePorNome(nomeProduto) : null; // localiza o estoque correspondente

            if (estoqueDoItem == null) { // produto não tem controle de estoque cadastrado
                System.out.println("\nProduto sem controle de estoque cadastrado (ID " + pedido.getKey() + ").");
                return false; // bloqueia a compra
            }
            if (!estoqueDoItem.disponivel(quantidadePedida)) { // checa se a quantidade pedida está disponível
                return false; // disponivel() já imprime o motivo (estoque insuficiente)
            }
        }

        // 2ª passada: já garantido que há estoque para tudo, agora abate de fato
        for (java.util.Map.Entry<Integer, Integer> pedido : pedidoPorId.entrySet()) { // percorre cada item pedido de novo
            String nomeProduto = nomeProdutoPorId(pedido.getKey()); // descobre o nome do produto pelo id
            Estoque estoqueDoItem = buscarEstoquePorNome(nomeProduto); // localiza o estoque correspondente
            estoqueDoItem.removeProduto(pedido.getValue()); // retira do estoque a quantidade comprada
        }

        return true; // estoque validado e abatido com sucesso
    }

    //  CLIENTE
    static void fluxoCliente() {

        int opcao = -1; // controla o loop do submenu de cliente

        while (opcao != 0) { // repete até o usuário escolher voltar (0)
            System.out.println("\n========== MENU CLIENTE ==========");
            System.out.println("1 - Cadastrar novo cliente");
            System.out.println("2 - Buscar cliente (CPF)");
            System.out.println("3 - Listar todos os clientes");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha: ");
            opcao = teclado.nextInt();   // lê a opção escolhida
            teclado.nextLine();          // limpa o buffer após a entrada do inteiro

            switch (opcao) {
                case 1:
                    TipoCliente novo = cadastrarNovoCliente(); // coleta os dados e cria o cliente
                    clientes.add(novo);                        // adiciona o novo cliente à lista
                    System.out.println("Cliente cadastrado com sucesso!");
                    novo.mostraInfo();                          // exibe as informações do cliente criado
                    break;
                case 2:
                    TipoCliente encontrado = buscarCliente(); // procura cliente pelo CPF digitado
                    if (encontrado == null) {
                        System.out.println("Cliente não encontrado.");
                    } else {
                        encontrado.mostraInfo(); // exibe as informações do cliente encontrado
                    }
                    break;
                case 3:
                    if (clientes.isEmpty()) { // checa se já existe algum cliente cadastrado
                        System.out.println("Nenhum cliente cadastrado ainda.");
                    } else {
                        System.out.println("\n--- CLIENTES CADASTRADOS ---");
                        for (TipoCliente c : clientes) { // percorre e exibe todos os clientes
                            c.mostraInfo();
                        }
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal..."); // sai do loop pelo while
                    break;
                default:
                    System.out.println("Opção inválida!"); // opção fora do menu
            }
        }
    }

    // coleta os dados do cliente pelo teclado e cria o objeto TipoCliente
    static TipoCliente cadastrarNovoCliente() {
        System.out.println("========== CADASTRO DE CLIENTE ==========");

        System.out.println("Digite o nome do cliente: ");
        String nome = teclado.nextLine(); // lê o nome digitado

        System.out.println("Digite o CPF do cliente: ");
        String cpf = teclado.nextLine(); // lê o CPF digitado

        System.out.println("Digite o telefone do cliente: ");
        String telefone = teclado.nextLine(); // lê o telefone digitado

        System.out.println("Digite o email do cliente: ");
        String email = teclado.nextLine(); // lê o email digitado

        System.out.println("Digite o endereço do cliente: ");
        String endereco = teclado.nextLine(); // lê o endereço digitado

        return new TipoCliente(nome, cpf, telefone, email, endereco); // cria e retorna o cliente
    }

    // procura um cliente já cadastrado pelo CPF digitado
    static TipoCliente buscarCliente() {
        System.out.println("Digite o CPF do cliente: ");
        String busca = teclado.nextLine(); // lê o CPF a ser buscado

        for (TipoCliente c : clientes) { // percorre todos os clientes cadastrados
            if (c.getCpf().equals(busca)) { // compara o CPF de cada cliente com o buscado
                System.out.println("Cliente encontrado: " + c.getNome());
                return c; // retorna o cliente encontrado
            }
        }
        return null; // percorreu a lista toda e não achou
    }

    // Identifica o cliente (novo ou já cadastrado). Retorna null se usuário não encontrar o cliente e optar por não cadastrá-lo.
    static TipoCliente identificarOuCadastrarCliente() {

        System.out.println("\n1 - Cliente novo");
        System.out.println("2 - Cliente já cadastrado");
        System.out.print("Escolha: ");
        int tipo = teclado.nextInt(); // lê se é cliente novo (1) ou já cadastrado (2)
        teclado.nextLine();           // limpa o buffer após a entrada do inteiro

        TipoCliente cliente; // vai guardar o cliente identificado ao final do método

        if (tipo == 1) { // cliente novo
            cliente = cadastrarNovoCliente();    // coleta os dados e cria o cliente
            clientes.add(cliente);               // adiciona à lista de clientes
            System.out.println("Cliente cadastrado com sucesso!");
        } else { // cliente já cadastrado
            cliente = buscarCliente(); // procura o cliente pelo CPF

            if (cliente == null) { // não encontrou o cliente buscado
                System.out.println("Cliente não encontrado.");
                System.out.println("Deseja cadastrá-lo agora? (1 - Sim / 2 - Não): ");
                int resposta = teclado.nextInt(); // lê a escolha de cadastrar ou não
                teclado.nextLine();                // limpa o buffer

                if (resposta == 1) { // optou por cadastrar agora
                    cliente = cadastrarNovoCliente();
                    clientes.add(cliente);
                    System.out.println("Cliente cadastrado com sucesso!");
                } else { // optou por não cadastrar
                    System.out.println("Não cadastrado.");
                    return null; // sem cliente identificado
                }
            }
        }
        return cliente; // retorna o cliente novo ou encontrado
    }

    //  REGISTRAR COMPRA
    static void fluxoRegistrarCompra() {

        System.out.println("\n========== REGISTRAR COMPRA ==========");
        TipoCliente cliente = identificarOuCadastrarCliente(); // identifica ou cadastra o cliente da compra

        if (cliente == null) { // não foi possível identificar o cliente
            System.out.println("Compra não registrada.");
            return; // sem cliente não há como continuar
        }

        // ── a partir daqui o cliente já está identificado: abre o carrinho ──
        Carrinho carrinho = new Carrinho(); // cria um carrinho novo para esta compra

        // produtos de teste, carregados sem exibir "Produto cadastrado: ..." no console
        carregarProdutosSemImprimir(carrinho); // popula o catálogo do carrinho com os produtos da loja

        // rastreia em paralelo o que foi pedido (id do produto -> quantidade),
        // necessário porque o Carrinho não expõe os itens publicamente
        java.util.Map<Integer, Integer> pedidoPorId = new java.util.HashMap<>(); // guarda id -> quantidade pedida

        int escolha = -1; // controla o loop do submenu do carrinho

        while (escolha != 5) { // repete até o usuário escolher finalizar a compra (5)

            System.out.println("\n|======== CARRINHO DE " + cliente.getNome() + " ========|");
            System.out.println("|1 -> Ver produtos disponiveis");
            System.out.println("|2 -> Adicionar produto ao carrinho");
            System.out.println("|3 -> Remover produto do carrinho");
            System.out.println("|4 -> Ver carrinho");
            System.out.println("|5 -> Finalizar compra");
            System.out.println("|Escolhe uma opção");

            escolha = teclado.nextInt(); // lê a opção escolhida no submenu do carrinho

            switch (escolha) {
                case 1:
                    carrinho.listrCatalogo(); // exibe a lista de produtos disponíveis
                    break;

                case 2:
                    System.out.print("Digite o ID do produto: ");
                    int idAdicionar = teclado.nextInt(); // lê o id do produto a adicionar
                    System.out.print("Digite a quantidade: ");
                    int quantidade = teclado.nextInt(); // lê a quantidade desejada
                    carrinho.adicionarAoCarrinho(idAdicionar, quantidade); // adiciona o item ao carrinho
                    pedidoPorId.merge(idAdicionar, quantidade, Integer::sum); // soma a quantidade no rastreamento paralelo
                    break;

                case 3:
                    System.out.print("Digite o ID do produto para remover: ");
                    int idRemover = teclado.nextInt(); // lê o id do produto a remover
                    carrinho.removerProduto(idRemover); // remove o item do carrinho
                    pedidoPorId.remove(idRemover); // remoção tira o item inteiro do carrinho
                    break;

                case 4:
                    carrinho.listarItensCarrinho(); // exibe os itens já adicionados ao carrinho
                    break;

                case 5:
                    System.out.println("Finalizando compra..."); // sai do loop pelo while
                    break;

                default:
                    System.out.println("Opção inválida! Digite um número entre 1 e 5."); // opção fora do menu
                    break;
            }
        }

        // ── valida e abate do estoque ANTES de fechar a compra ──
        if (!validarEAbaterEstoque(pedidoPorId)) { // checa se há estoque suficiente para tudo
            System.out.println("\nCompra NÃO finalizada por falta de estoque. Ajuste o carrinho e tente novamente.");
            return; // interrompe o fluxo sem fechar a compra
        }

        // ── fecha a compra numerada, vinculando cliente + carrinho ──
        Compra compra = new Compra(cliente, carrinho); // cria a compra com número sequencial automático
        cliente.registrarCompra(); // mantém a contagem de compras do cliente (nível comum/premium)
        compra.mostraResumo(); // exibe o resumo da compra no console
        System.out.println("Compra registrada com sucesso!");

        // ── total do carrinho vai direto para o pagamento, sem digitar valor ──
        double totalCompra = compra.getTotal(); // pega o valor total do carrinho

        if (totalCompra <= 0) { // carrinho vazio, nada para cobrar
            System.out.println("\nCarrinho vazio — nada a pagar.");
            return; // não cria pagamento de valor zero
        }

        System.out.println("\nVamos prosseguir para o pagamento desta compra.");

        // aplica o desconto conforme o nível do cliente (Comum/Premium)
        String tipoClienteDesconto = (cliente.getNivel() == TipoCliente.PREMIUM) ? "premium" : "comum"; // define o tipo para o desconto
        Desconto desconto = new Desconto(tipoClienteDesconto); // cria o desconto de acordo com o nível do cliente
        double totalComDesconto = desconto.calculaDesconto(totalCompra); // calcula o valor já com o desconto aplicado

        System.out.println("\n----- DESCONTO APLICADO -----");
        System.out.println(desconto); // mostra o tipo de cliente e o percentual de desconto
        System.out.printf("Total do carrinho : R$ %.2f%n", totalCompra);
        System.out.printf("Total com desconto: R$ %.2f%n", totalComDesconto);
        System.out.println("------------------------------");

        Pagamento pagamento = new Pagamento(totalComDesconto); // cria o pagamento já com o valor final, sem digitação

        int opPag = -1; // controla o loop do submenu de pagamento

        while (opPag != 0) { // repete até o pagamento ser concluído ou cancelado

            System.out.println("\n--- MENU DE PAGAMENTO ---");
            System.out.println("1 - Pagar via Pix");
            System.out.println("2 - Pagar via Crédito");
            System.out.println("3 - Pagar via Débito");
            System.out.println("4 - Cancelar pagamento");
            System.out.println("5 - Emitir comprovante (console + .txt)");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha: ");
            opPag = teclado.nextInt(); // lê a opção do menu de pagamento

            switch (opPag) {
                case 1:
                    pagamento.pix(teclado); // processa o pagamento via Pix
                    opPag = 0; // força a saída do loop após o pagamento
                    break;
                case 2:
                    pagamento.credito(teclado); // processa o pagamento via crédito
                    opPag = 0;
                    break;
                case 3:
                    pagamento.debito(teclado); // processa o pagamento via débito
                    opPag = 0;
                    break;
                case 4:
                    pagamento.cancelar(); // cancela o pagamento
                    opPag = 0;
                    break;
                case 5:
                    System.out.print("Deseja emitir o comprovante?(1-SIM/2-NÃO: ");
                    int opcaoComp = teclado.nextInt(); // lê se deseja emitir o comprovante
                    if (opcaoComp == 1) {
                        pagamento.emitirComprovante(); // emite o comprovante no console e em .txt
                        opPag = 0;
                    } else {
                        System.out.println("Comprovante não emitido."); // não emite, permanece no menu
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal..."); // sai do loop pelo while
                    break;
                default:
                    System.out.println("Opção inválida!"); // opção fora do menu
            }
        }
    }


    //  ESTOQUE
    static void fluxoEstoque() {

        int opcao = -1; // controla o loop do submenu de estoque

        while (opcao != 0) { // repete até o usuário escolher voltar (0)

            System.out.println("\n--- ESTOQUE ---");
            for (int i = 0; i < estoques.length; i++) { // percorre todos os itens do estoque
                System.out.printf("%d - %-40s (Estoque: %d)%n",
                        i + 1,                          // número do item na lista (base 1)
                        estoques[i].getTipoProduto(),   // nome do produto
                        estoques[i].getQtdProdutos());  // quantidade atual em estoque
            }
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha o produto (número): ");
            opcao = teclado.nextInt(); // lê o número do produto escolhido

            if (opcao == 0) { // usuário escolheu voltar
                System.out.println("Voltando ao menu principal...");
                break; // sai do loop externo
            }

            int idxProd = opcao - 1; // converte para índice do array (base 0)

            if (idxProd < 0 || idxProd >= estoques.length) { // valida se o índice existe no array
                System.out.println("Produto inválido!");
                continue; // volta a mostrar a lista de estoque
            }

            Estoque estoque = estoques[idxProd]; // referencia o item de estoque escolhido

            int opEst = -1; // controla o loop do submenu do item de estoque

            while (opEst != 0) { // repete até o usuário escolher voltar (0)

                System.out.println("\n--- MENU DE ESTOQUE ---");
                System.out.println("Produto : " + estoque.getTipoProduto());   // nome do produto escolhido
                System.out.println("Qtd atual: " + estoque.getQtdProdutos());   // quantidade atual
                System.out.println("1 - Adicionar produtos");
                System.out.println("2 - Remover produtos");
                System.out.println("3 - Verificar disponibilidade");
                System.out.println("0 - Voltar");
                System.out.print("Escolha: ");
                opEst = teclado.nextInt(); // lê a opção do menu de estoque

                switch (opEst) {
                    case 1:
                        System.out.print("Quantos produtos deseja adicionar? ");
                        estoque.addProduto(teclado.nextInt()); // soma a quantidade ao estoque
                        break;
                    case 2:
                        System.out.print("Quantos produtos deseja remover? ");
                        estoque.removeProduto(teclado.nextInt()); // subtrai a quantidade do estoque
                        break;
                    case 3:
                        System.out.print("Quantos produtos deseja verificar? ");
                        estoque.disponivel(teclado.nextInt()); // verifica se a quantidade está disponível
                        break;
                    case 0:
                        System.out.println("Voltando ao menu anterior..."); // sai do loop pelo while
                        break;
                    default:
                        System.out.println("Opção inválida!"); // opção fora do menu
                }
            }

            opcao = -1; // volta a mostrar a lista de estoque, mantendo o loop externo aberto
        }
    }

    //  ITENS DA LOJA
    static void fluxoItensDaLoja() {

        Carrinho catalogo = new Carrinho();           // cria um carrinho só para usar seu catálogo
        carregarProdutosSemImprimir(catalogo);        // popula o catálogo com os produtos da loja

        System.out.println("\n========== ITENS DA LOJA ==========");
        catalogo.listrCatalogo();                     // exibe a lista de produtos disponíveis
        System.out.println("====================================");
    }
}