package org.example;

import java.util.Objects; // utilitário usado para gerar o hashCode a partir do id

public class Produto {

    private int id;        // identificador único do produto
    private double preco;  // preço unitário do produto
    private String nome;   // nome/descrição do produto

    public Produto(int id, double preco, String nome) { // construtor: recebe id, preço e nome do produto
        this.id = id;       // guarda o id recebido
        this.preco = preco; // guarda o preço recebido
        this.nome = nome;   // guarda o nome recebido
    }

    @Override
    public boolean equals(Object o){ // define quando dois produtos são considerados "iguais"
        if (this == o) // mesma referência de memória
            return true;
        if (!(o instanceof Produto)) // o objeto comparado não é um Produto
            return false;

        return id == ((Produto) o).id; // dois produtos são iguais se tiverem o mesmo id
    }

    @Override
    public int hashCode(){ // gera o código hash do produto, usado em HashMap/HashSet
        return Objects.hash(id); // hash baseado apenas no id, coerente com o equals acima
    }

    public String getNome() {
        return nome; // retorna o nome do produto
    }

    public void setNome(String nome) {
        this.nome = nome; // atualiza o nome do produto
    }

    public int getId() {
        return id; // retorna o id do produto
    }

    public void setId(int id) {
        this.id = id; // atualiza o id do produto
    }

    public double getPreco() {
        return preco; // retorna o preço do produto
    }

    public void setPreco(double preco) {
        this.preco = preco; // atualiza o preço do produto
    }
}