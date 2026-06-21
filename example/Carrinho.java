package org.example;

import java.util.HashMap; // implementação concreta de Map usada para guardar catálogo e itens
import java.util.Map;      // interface usada para declarar os atributos do carrinho

public class Carrinho {
    private Map<Integer, Produto> catalogo = new HashMap<>(); // catálogo: id do produto -> produto
    private Map<Produto, Integer> item = new HashMap<>();     // itens do carrinho: produto -> quantidade

    public void listrCatalogo (){ // exibe todos os produtos cadastrados no catálogo
        System.out.println("|======== PRODUTOS DISPONIVEIS ========|");
        for (Map.Entry<Integer,Produto> entrada : catalogo.entrySet()){ // percorre cada entrada do catálogo
            Produto produto = entrada.getValue(); // pega o produto da entrada atual
            System.out.println("ID: " + produto.getId() + " | " + produto.getNome() + " | R$ " + String.format("%.2f", produto.getPreco())); // imprime id, nome e preço formatado
        }
    }

    public void cadastrarNovoProduto (Produto produto){ // adiciona um produto novo ao catálogo
        catalogo.put(produto.getId(), produto); // guarda o produto usando seu id como chave
        System.out.println("Produto cadastrado: " + produto.getNome()); // confirma o cadastro no console
    }

    public Produto buscaProdutoId (int id){ // procura um produto no catálogo pelo id
        Produto produtoEncontrado = catalogo.get(id); // busca no Map pelo id informado
        return produtoEncontrado; // retorna o produto encontrado (ou null se não existir)
    }

    public void adicionarAoCarrinho (int idProduto, int quantidade){ // adiciona uma quantidade de um produto ao carrinho
        Produto produto = buscaProdutoId(idProduto); // localiza o produto pelo id no catálogo

        if(produto == null){ // produto não existe no catálogo
            System.out.println("Produto não encontrado no catalogo! ");
            return; // interrompe, não há o que adicionar
        }
        if(item.containsKey(produto)){ // produto já está no carrinho
            int quantidadeAtual = item.get(produto); // pega a quantidade já existente
            int novaQuantidade = quantidadeAtual + quantidade; // soma com a nova quantidade
            item.put(produto,novaQuantidade); // atualiza a quantidade no carrinho
        } else { // produto ainda não está no carrinho
            item.put(produto,quantidade); // adiciona o produto com a quantidade informada
        }
        System.out.println(quantidade + " unidade(s) de " + produto.getNome() + " adicionado(s) ao carrinho! "); // confirma a adição
    }

    public void removerProduto (int idProduto) { // remove um produto inteiro do carrinho
        Produto produto = buscaProdutoId(idProduto); // localiza o produto pelo id no catálogo

        if(produto == null){ // produto não existe no catálogo
            System.out.println("Produto não encontrado no catalogo! ");
            return; // interrompe, não há o que remover
        }
        if(item.containsKey(produto)){ // produto está presente no carrinho
            item.remove(produto); // remove o produto do carrinho
            System.out.println(produto.getNome() + " removido do carrinho! "); // confirma a remoção
        } else { // produto não estava no carrinho
            System.out.println("Esse produto não estava no carrinho! ");
        }


    }

    public double calcularTotalCarrinho (){ // calcula o valor total de todos os itens do carrinho
        double total = 0; // acumulador do valor total

        for(Map.Entry<Produto,Integer> entrada : item.entrySet()){ // percorre cada item do carrinho
            Produto produto = entrada.getKey();      // produto do item atual
            int quantidade = entrada.getValue();      // quantidade comprada desse produto

            double subtotal = produto.getPreco() * quantidade; // calcula o subtotal do item
            total = total + subtotal; // soma o subtotal ao total geral
        }

        return total; // retorna o valor total do carrinho
    }

    public void listarItensCarrinho (){ // exibe todos os itens do carrinho e o valor total
        System.out.println("======== Itens no carrinho ========");

        for(Map.Entry<Produto,Integer> entrada : item.entrySet()){ // percorre cada item do carrinho
            Produto produto = entrada.getKey();      // produto do item atual
            int quantidade = entrada.getValue();      // quantidade comprada desse produto
            System.out.println(produto.getNome() + " | Quantidade: " + quantidade + " | Preço por unidade: " + produto.getPreco()); // imprime os dados do item
        }

        System.out.printf("Valor total: R$ %.2f\n", calcularTotalCarrinho()); // imprime o valor total do carrinho
    }

    public void esvaziarCarrinho(){ // remove todos os itens do carrinho
        item.clear(); // limpa o Map de itens
        System.out.println("Carrinho vazio! "); // confirma que o carrinho foi esvaziado
    }

}