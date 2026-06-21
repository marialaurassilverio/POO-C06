package org.example;

public class Estoque {

    // Atributos
    private int qtdProdutos;//Quantidade de produtos
    private String tipoProduto;//estilo do produto

    // Construtor
    public Estoque(String tipoProduto, int qtdProdutos) {//Recebe esses dados
        this.tipoProduto = tipoProduto;//Atributo recebe o nome do produto
        this.qtdProdutos = qtdProdutos;//Atributo recebe a quantidade
    }

    // Adiciona produtos ao estoque
    public void addProduto(int quantidade) {//Quantidade a receber
        this.qtdProdutos += quantidade;//Soma o valor digitado com o estoque
        System.out.println(quantidade + " unidade(s) de \"" + tipoProduto + "\" adicionada(s) ao estoque.");
        System.out.println("Estoque atual: " + qtdProdutos);
    }

    // Remove produtos do estoque
    public void removeProduto(int quantidade) {
        if (quantidade > qtdProdutos) {//Verifica se a Quantidade é disponivel
            System.out.println("Estoque insuficiente! Disponível: " + qtdProdutos);
        } else {
            this.qtdProdutos -= quantidade;//Retira o valor digitado do estoque
            System.out.println(quantidade + " unidade(s) de \"" + tipoProduto + "\" removida(s) do estoque.");
            System.out.println("Estoque atual: " + qtdProdutos);
        }
    }

    // Verifica se tem o produto disponível na quantidade desejada
    public boolean disponivel(int quantidade) {//retorno de false e true
        if (qtdProdutos >= quantidade) {//Verifica se o estoque está disponivel
            System.out.println("\"" + tipoProduto + "\" disponível em estoque. Qtd solicitada: " + quantidade);
            return true;
        } else {
            System.out.println("\"" + tipoProduto + "\" indisponível. Estoque: " + qtdProdutos + " | Solicitado: " + quantidade);
            return false;//retona falso,se nao tiver
        }
    }

    // Getters e Setters
    public int getQtdProdutos() {
        //Retorna o valor do atributo privado QtdProdutos
        return qtdProdutos;
    }
    public void setQtdProdutos(int qtdProdutos) {
        //Altera o valor do atributo privado
        this.qtdProdutos = qtdProdutos;
    }

    public String getTipoProduto() {
        //retorna o valor do atributo privado string
        return tipoProduto;
    }
    public void setTipoProduto(String tipoProduto) {
        //Altera o atributo
        this.tipoProduto = tipoProduto;
    }
}
