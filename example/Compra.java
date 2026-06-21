package org.example;

public class Compra {

    // contador estático: compartilhado por todas as instâncias,
    // garante que cada compra ganhe um número novo e sequencial
    private static int proximoNumero = 1; // próximo número disponível para a próxima compra criada

    private int numero;            // número sequencial desta compra específica
    private TipoCliente cliente;   // cliente que realizou esta compra
    private Carrinho carrinho;     // carrinho com os itens comprados nesta compra

    public Compra(TipoCliente cliente, Carrinho carrinho) { // construtor: recebe o cliente e o carrinho da compra
        this.numero = proximoNumero++; // atribui o número atual e já incrementa pra próxima compra
        this.cliente = cliente;        // guarda o cliente recebido
        this.carrinho = carrinho;      // guarda o carrinho recebido
    }

    public int getNumero() {
        return numero; // retorna o número sequencial desta compra
    }

    public TipoCliente getCliente() {
        return cliente; // retorna o cliente vinculado a esta compra
    }

    public Carrinho getCarrinho() {
        return carrinho; // retorna o carrinho vinculado a esta compra
    }

    public double getTotal() {
        return carrinho.calcularTotalCarrinho(); // delega ao Carrinho o cálculo do valor total
    }

    // exibe o resumo final da compra no console
    public void mostraResumo() {
        System.out.println("\n========== RESUMO DA COMPRA ==========");
        System.out.println("Compra Nº: " + numero); // mostra o número desta compra
        System.out.println("Cliente  : " + cliente.getNome() + " (CPF: " + cliente.getCpf() + ")"); // mostra nome e CPF do cliente
        carrinho.listarItensCarrinho(); // lista os itens comprados e o valor total
        System.out.println("========================================");
    }
}